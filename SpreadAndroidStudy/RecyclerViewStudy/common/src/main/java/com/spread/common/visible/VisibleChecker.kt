package com.spread.common.visible

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import kotlin.concurrent.thread
import kotlin.math.max
import kotlin.math.min

class VisibleChecker(ee: ICheckEE) {

    companion object {
        private const val VISIBLE_CHECK_TIMEOUT = 10L * 1000L
    }

    data class Point(val x: Int, val y: Int)

    data class Area(val origin: Point, val width: Int, val height: Int) {
        override fun toString() = StringBuilder().apply {
            appendLine("origin: x(${origin.x}), y(${origin.y})")
            appendLine("width: $width")
            appendLine("height: $height")
        }.toString()
    }

    data class DetectArea(
        val area: Area,
        var currSize: Int = 0,
        @Volatile var valid: Boolean = false
    )

    interface IViewChecked {
        fun validArea(): Area
    }

    interface ICheckEE {
        val rootView: View
        val totalWidth: Int
        val totalHeight: Int
        val expectedTime: Long
        val validRatio: Float
    }

    private val rootView = ee.rootView
    private val totalHeight = ee.totalHeight
    private val totalWidth = ee.totalWidth
    private val expectedTime = ee.expectedTime
    private val validRatio = ee.validRatio

    private val matrix = arrayOf(
        arrayOf(DetectArea(Area(Point(0, 0), totalWidth, totalHeight / 2))),
        arrayOf(DetectArea(Area(Point(0, totalHeight / 2), totalWidth, totalHeight / 2)))
    )

    @Volatile
    private var run = true

    private var startTime = 0L

    private val currTimeCost
        get() = System.currentTimeMillis() - startTime

    /**
     * Won't be called when notifyXXX of adapter is emitted.
     * Because after RV consumed pending OPs added during notify,
     * it will first intercept layout, which means requestLayout()
     * of RV will do nothing. Then RV will measure and layout its
     * children by itself. Thus, the layout request won't be
     * passed to ViewRootImpl. onGlobalLayout() won't be called either.
     * TODO: things above should appear in my paper later.
     */
    private val layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        if (checkValidity()) {
            return@OnGlobalLayoutListener
        }
        Log.d(
            "Spread-Visi",
            "receive layout callback after: ${System.currentTimeMillis() - startTime}ms"
        )
        Log.d("Spread-Visi", "traversal start")
        traversalRecursion(rootView)
        Log.d("Spread-Visi", "traversal stop")
        if (currTimeCost < VISIBLE_CHECK_TIMEOUT) {
            Log.d("Spread-Visi", "current time cost: ${currTimeCost}ms")
            rootView.requestLayout()
        }
    }

    private val scrollListener = ViewTreeObserver.OnScrollChangedListener {
        if (checkValidity()) {
            return@OnScrollChangedListener
        }
        Log.d("Spread-Visi", "receive on scroll listener")
        traversalRecursion(rootView)
        checkValidity()
    }

    fun manualTraversal() {
        Log.e("Spread-Visi", "traversal start")
        traversalRecursion(rootView)
        Log.e("Spread-Visi", "traversal stop")
        forEachMatrix {
            Log.d(
                "Spread-Visi",
                "valid: ${it.valid}, currSize: ${it.currSize}, validThreshold: ${it.area.width * it.area.height}"
            )
        }
    }

    private fun traversalRecursion(view: View) {
        if (view is IViewChecked) {
            val validArea = view.validArea()
            Log.i("Spread-Visi", "cover matrix start: ${(view as? TextView)?.text}")
            coverMatrix(validArea)
            Log.i("Spread-Visi", "cover matrix stop")
        }
        if (view is ViewGroup) {
            val childCount = view.childCount
            for (i in 0..childCount) {
                val child = view.getChildAt(i)
                if (child != null) {
                    traversalRecursion(child)
                }
            }
        }
    }

    /**
     * If a view content can cover the detection area.
     * Not the view itself, but the meaningful area.
     * For example, if you are a TextView, then ignore the padding or any
     * area without the text.
     * @param area meaningful area
     */
    private fun coverMatrix(area: Area) {
        forEachMatrix {
            val detectArea = it.area
            var currSize = it.currSize
            val updated = it.valid
            if (!updated) {
                val size = calAreaIntersectionSize(area, detectArea)
                Log.d("Spread-Visi", "area coverage size: $size")
                currSize += size
                Log.d("Spread-Visi", "current size: $currSize")
                if (currSize > detectArea.width * detectArea.height * validRatio) {
                    it.valid = true
                }
                it.currSize = currSize
            }
        }
    }

    private fun forEachMatrix(block: (DetectArea) -> Unit) {
        for (i in matrix.indices) {
            val line = matrix[i]
            for (j in line.indices) {
                block(line[j])
            }
        }
    }

    private fun forEachMatrixIndexed(block: (DetectArea, Int, Int) -> Unit) {
        for (i in matrix.indices) {
            val line = matrix[i]
            for (j in line.indices) {
                block(line[j], i, j)
            }
        }
    }

    /**
     * Calculate intersection area size
     */
    private fun calAreaIntersectionSize(a1: Area, a2: Area): Int {
        Log.d(
            "Spread-Visi", "calculating intersection size for: \n" +
                    "area1: $a1" +
                    "area2: $a2"
        )
        val left1 = a1.origin.x
        val right1 = left1 + a1.width
        val left2 = a2.origin.x
        val right2 = left2 + a2.width
        val top1 = a1.origin.y
        val bottom1 = top1 + a1.height
        val top2 = a2.origin.y
        val bottom2 = top2 + a2.height
        if (right2 <= left1 || left2 >= right1 || bottom2 <= top1 || top2 >= bottom1) {
            return 0
        }
        val left = max(left1, left2)
        val right = min(right1, right2)
        val top = max(top1, top2)
        val bottom = min(bottom1, bottom2)
        return (right - left) * (bottom - top)
    }


    /**
     * 现象：expectedTime为3s内，就能直接触发检测；反之的话要等到RV布局结束
     * 才能执行。3s为FakeNetwork的延时时间。其实就是因为3s之后，Adapter有数据
     * 了开始notify，同时布局的过程还很长，因为SlowTextView。因此消息很大，
     * 导致这个消息插不进去。
     */
    // TODO: 说明这里为什么有3秒的限制，和RV抑制requestLayout有关？无关！
    // TODO: 为什么超过3秒的情况下Log打印也被抑制了，和Handler消息机制有关
    // TODO: 是因为RV布局的消息很大，每一次notifyInsert都会产生一个大消息
    fun start() {
        startTime = System.currentTimeMillis()
        Log.d("Spread-Visi", "start visibility check!")
//        rootView.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
        rootView.viewTreeObserver.addOnScrollChangedListener(scrollListener)
        thread {
            Thread.sleep(expectedTime)
//            rootView.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
            rootView.viewTreeObserver.removeOnScrollChangedListener(scrollListener)
//            checkValidity()
        }
    }

    fun stop() {
//        rootView.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
        run = false
        for (i in matrix.indices) {
            val line = matrix[i]
            for (j in line.indices) {
                Log.d("Spread-Visi", "area $j valid: ${line[j].valid}")
            }
        }
        Log.d("Spread-Visi", "stop visibility check!")
    }

    /**
     * @return true means every matrix is valid; false means the opposite.
     */
    private fun checkValidity(): Boolean {
        Log.d(
            "Spread-Visi",
            "check matrix validity after ${System.currentTimeMillis() - startTime}ms"
        )
        var good = true
        forEachMatrixIndexed { matrix, i, j ->
            val valid = matrix.valid
            Log.d("Spread-Visi", "area [$i][$j] valid: $valid")
            if (!valid) {
                good = false
            }
        }
        return good
    }
}