package com.example.customviewtest.customview.gesture

import android.util.Log
import android.view.MotionEvent
import kotlin.math.PI
import kotlin.math.atan2

class RotateGestureDetector(private val mListener: OnRotateListener) {

    private var mPrevEvent: MotionEvent? = null
    private var mCurrEvent: MotionEvent? = null

    private var mPrevFingerDiffX = 0F
    private var mPrevFingerDiffY = 0F
    private var mCurrFingerDiffX = 0F
    private var mCurrFingerDiffY = 0F

    private var mInProgress = false

    private var rotateIndex0 = 0
    private var rotateIndex1 = 1

    fun onTouchEvent(event: MotionEvent) = kotlin.runCatching { handleEvent(event) }.isSuccess

    val degree: Float
        get() {
            var a1 = atan2(mCurrFingerDiffY, mCurrFingerDiffX)
            val a2 = atan2(mPrevFingerDiffY, mPrevFingerDiffX)
            if ((a1 < 0 && a2 > 0) || (a1 > 0 && a2 < 0)) {
                // fix: sudden reverse when two fingers at:  -> O <-
                a1 = -a1
            }
            return ((a1 - a2) * 100 / PI).toFloat()
        }

    private fun log() {
        val builder = StringBuilder().apply {
            appendLine("mCurrFingerDiffY: $mCurrFingerDiffY")
            appendLine("mCurrFingerDiffX: $mCurrFingerDiffX")
            appendLine("mPrevFingerDiffY: $mPrevFingerDiffY")
            appendLine("mPrevFingerDiffX: $mPrevFingerDiffX")
            appendLine("arc tan 1: ${atan2(mCurrFingerDiffY, mCurrFingerDiffX)}")
            appendLine("arc tan 2: ${atan2(mPrevFingerDiffY, mPrevFingerDiffX)}")
        }
        Log.d("Spread-Rotate", builder.toString())
    }

    private fun handleEvent(event: MotionEvent) {
        val action = event.actionMasked
        val streamComplete = action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
        if (action == MotionEvent.ACTION_DOWN || streamComplete) {
            if (mInProgress) {
                mListener.onRotateEnd(this)
            }
            reset()
            return
        }
        val count = event.pointerCount
        if (action == MotionEvent.ACTION_POINTER_UP) {
            if (count < 2) {
                if (mInProgress) {
                    mListener.onRotateEnd(this)
                }
                reset()
            } else {
                updateRotateIndex(event)
                mPrevEvent = null
            }
            return
        }
        if (action == MotionEvent.ACTION_POINTER_DOWN) {
            mPrevEvent = MotionEvent.obtain(event)
            updateRotateIndex(event)
            updateStateByEvent(event)
            mInProgress = mListener.onRotateBegin(this)
        }
        if (action == MotionEvent.ACTION_MOVE && count >= 2) {
            if (mPrevEvent == null) {
                mPrevEvent = MotionEvent.obtain(event)
            }
            updateStateByEvent(event)
            if (mListener.onRotate(this)) {
                mPrevEvent?.recycle()
                mPrevEvent = MotionEvent.obtain(event)
            }
        }
        return
    }

    private fun updateRotateIndex(event: MotionEvent) {
        val count = event.pointerCount
        if (count <= 2) {
            rotateIndex0 = 0
            rotateIndex1 = 1
            return
        }
        val pointerUpIndex = if (event.actionMasked == MotionEvent.ACTION_POINTER_UP) event.actionIndex else Int.MAX_VALUE
        var minX = Float.MAX_VALUE
        var minY = Float.MAX_VALUE
        var maxX = 0F
        var maxY = 0F
        var minXIndex = 0
        var minYIndex = 0
        var maxXIndex = 0
        var maxYIndex = 0
        for (i in 0 until count) {
            if (i == pointerUpIndex) {
                continue
            }
            val x = event.x
            val y = event.y
            if (x <= minX) {
                minX = x
                minXIndex = i
            }
            if (x >= maxX) {
                maxX = x
                maxXIndex = i
            }
            if (y <= minY) {
                minY = y
                minYIndex = i
            }
            if (y >= maxY) {
                maxY = y
                maxYIndex = i
            }
        }
        if (maxX - minX > maxY - minY) {
            rotateIndex0 = if (minXIndex >= pointerUpIndex) minXIndex - 1 else minXIndex
            rotateIndex1 = if (maxXIndex >= pointerUpIndex) maxXIndex - 1 else maxXIndex
        } else {
            rotateIndex0 = if (minYIndex >= pointerUpIndex) minYIndex - 1 else minYIndex
            rotateIndex1 = if (maxYIndex >= pointerUpIndex) maxYIndex - 1 else maxYIndex
        }
    }

    private fun updateStateByEvent(event: MotionEvent) {
        mCurrEvent?.apply {
            recycle()
            mCurrEvent = null
        }
        mCurrEvent = MotionEvent.obtain(event)
        val prev = mPrevEvent
        val curr = mCurrEvent

        if (prev == null || curr == null) {
            return
        }

        val prevX0 = prev.getX(rotateIndex0)
        val prevY0 = prev.getY(rotateIndex0)
        val prevX1 = prev.getX(rotateIndex1)
        val prevY1 = prev.getY(rotateIndex1)
        val prevXDistance = prevX1 - prevX0
        val prevYDistance = prevY1 - prevY0
        mPrevFingerDiffX = prevXDistance
        mPrevFingerDiffY = prevYDistance

        val currX0 = curr.getX(rotateIndex0)
        val currY0 = curr.getY(rotateIndex0)
        val currX1 = curr.getX(rotateIndex1)
        val currY1 = curr.getY(rotateIndex1)
        val currXDistance = currX1 - currX0
        val currYDistance = currY1 - currY0
        mCurrFingerDiffX = currXDistance
        mCurrFingerDiffY = currYDistance
    }

    private fun reset() {
        mInProgress = false
        mPrevEvent?.apply {
            recycle()
            mPrevEvent = null
        }
        mCurrEvent?.apply {
            recycle()
            mCurrEvent = null
        }
    }

    interface OnRotateListener {
        fun onRotateBegin(detector: RotateGestureDetector): Boolean
        fun onRotate(detector: RotateGestureDetector): Boolean
        fun onRotateEnd(detector: RotateGestureDetector)
    }

    abstract class SimpleRotateListener : OnRotateListener {
        override fun onRotateBegin(detector: RotateGestureDetector) = true
        override fun onRotate(detector: RotateGestureDetector) = false
        override fun onRotateEnd(detector: RotateGestureDetector) {

        }
    }

}