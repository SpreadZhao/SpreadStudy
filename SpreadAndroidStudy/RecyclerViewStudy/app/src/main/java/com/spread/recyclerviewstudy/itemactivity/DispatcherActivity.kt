package com.spread.recyclerviewstudy.itemactivity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Display
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.preload.prebind.DefaultVisibilityListener
import com.spread.common.preload.prebind.DispatcherRecyclerView
import com.spread.common.preload.prebind.Occasion
import com.spread.common.preload.prebind.ViewHolderVisibilityDispatcher
import com.spread.common.preload.test.ItemType
import com.spread.common.preload.test.MyViewHolder
import com.spread.common.tool.BottomToolbarButtonsAdapter
import com.spread.common.tool.ButtonItem
import com.spread.recyclerviewstudy.R
import com.spread.recyclerviewstudy.tool.apm.IFrameTracer
import com.spread.recyclerviewstudy.tool.apm.MainLooperMonitor

class DispatcherActivity : AppCompatActivity(), IFrameTracer {

    companion object {
        private const val TAG = "DispatcherActivity-Spread"
    }

    private lateinit var recyclerView: DispatcherRecyclerView

    override var totalFrames = 0L
    override var lostFrames = 0L
    override val mWindow: Window
        get() = this.getWindow()
    override val mDisplay: Display?
        get() = this.getDisplay()


    private val testThread = object : Thread() {

        var looper: Looper? = null

        override fun run() {
            Looper.prepare()
            looper = Looper.myLooper()
            Looper.loop()
        }
    }

    init {
        testThread.start()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dispatcher)
        val rootView = findViewById<FrameLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.dispatcher_recyclerview)
        val adapter = MyAdapter()
        val layoutManager = object : LinearLayoutManager(this) {
            override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
                return height
            }

            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)
                dispatcher.dispatchVisibility(Occasion.LayoutComplete)
            }
        }.apply { isItemPrefetchEnabled = false }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
//    recyclerView.itemAnimator = null
//    PagerSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView.setItemViewCacheSize(0)
        recyclerView.setHasFixedSize(true)

        val bottomRV = findViewById<RecyclerView>(R.id.bottom_toolbar)
        bottomRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false).apply {
            isItemPrefetchEnabled = false
        }
        bottomRV.adapter = BottomToolbarButtonsAdapter(bottomRV, listOf(
            ButtonItem("Scroll") { recyclerView.scrollBy(0, 500) },
            ButtonItem("First Shrink") { adapter.firstShrink() },
            ButtonItem("Insert After 1") { adapter.insertOne() },
            ButtonItem("Delete Second") { adapter.deleteOne() },
            ButtonItem("Start Monitor") { MainLooperMonitor() },
            ButtonItem("Do sth") {
                Handler(Looper.getMainLooper()).post {
                    Log.d(
                        "SpreadAPM",
                        "doSth"
                    )
                }
            }
        ))
        dispatcher = ViewHolderVisibilityDispatcher.Builder()
            .bindRecyclerView(recyclerView)
            .setListener(DefaultVisibilityListener())
            .build()
        testThread.looper?.let {
            val handler = Handler(it)
            initFpsListener(handler)
        }
    }

    private lateinit var dispatcher: ViewHolderVisibilityDispatcher

    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

        private val dataSet =
            createListData(1..100, ItemType.DATA_TYPE_EVEN, ItemType.DATA_TYPE_ODD)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false)
                .apply {
                    background = if (viewType == ItemType.DATA_TYPE_EVEN) {
                        ColorDrawable(Color.parseColor("#CC0033"))
                    } else if (viewType == ItemType.DATA_TYPE_ODD) {
                        ColorDrawable(Color.parseColor("#0066CC"))
                    } else {
                        ColorDrawable(Color.parseColor("#f47920"))
                    }
                    layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        2199 / 3 + 10
                    )
                }
//      Thread.sleep(15)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = dataSet[position].str
            holder.textView.textSize = 100f
            holder.typeView.text = dataSet[position].type.toString()
            holder.textView.adjustGravity()
            holder.typeView.adjustGravityForType()

        }

        override fun getItemCount() = dataSet.size

        fun firstShrink() {
            val firstChild = recyclerView.getChildAt(0)
            firstChild.updateLayoutParams<ViewGroup.LayoutParams> {
                width = LayoutParams.MATCH_PARENT
                height = 2199 / 3
            }
        }

        fun insertOne() {
            val newItem = Data("Spread", ItemType.DATA_TYPE_OTHER)
            dataSet.add(1, newItem)
            notifyItemInserted(1)
        }

        fun deleteOne() {
            dataSet.removeAt(1)
            notifyItemRemoved(1)
        }

        override fun getItemViewType(position: Int) = dataSet[position].type
    }

}

fun IFrameTracer.initFpsListener(handler: Handler? = null) {
    mWindow.addOnFrameMetricsAvailableListener({ window, frameMetrics, dropCountSinceLastInvocation ->
        val copy = FrameMetrics(frameMetrics)
        val duration = copy.getMetric(FrameMetrics.TOTAL_DURATION)
        val frameIntervalNanos = 1000000000 / (mDisplay?.refreshRate ?: 60F)
        val calDroppedFrames = (duration / frameIntervalNanos).toInt()
        val instantFps = 1000000000 / duration
        val fps = if (instantFps < 60) {
            lostFrames++
            instantFps
        } else {
            60
        }
        totalFrames++
        val thread = if (Looper.myLooper() == Looper.getMainLooper()) {
            "Main Thread"
        } else {
            "Test Thread"
        }
        Log.d(
            "SpreadAPM",
            "Official: $dropCountSinceLastInvocation, my: $calDroppedFrames, fps: $fps, lost: ${lostFrames.toDouble() * 100 / totalFrames}%, thread: $thread"
        )
    }, handler)
}