package com.example.customviewtest.customview.gesture

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.TextView
import com.example.customviewtest.R

class GestureResizeLayout : GestureLayout {

    sealed interface Speed {
        val value get() = 0

        data object SLOW : Speed {
            override val value: Int
                get() = 1
        }

        data object NORMAL : Speed {
            override val value: Int
                get() = 2
        }

        data object FAST : Speed {
            override val value: Int
                get() = 3
        }
    }

    private var mIsResizing = false

    private var mTouchHandled = false

    private lateinit var mResetButton: TextView

    var mChild: View? = null

    var speed: Speed = Speed.NORMAL

    private val mScaleListener = object : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val factor = detector.scaleFactor
            mChild?.apply {
                scaleX *= factor
                scaleY *= factor
            }
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mTouchHandled = true
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
        }

    }

    private val mTransferListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            mTouchHandled = true
            mChild?.apply {
                translationX -= distanceX
                translationY -= distanceY
            }
            return true
        }
    }

    private val mRotateListener = object : RotateGestureDetector.SimpleRotateListener() {
        override fun onRotateBegin(detector: RotateGestureDetector): Boolean {
            mTouchHandled = true
            return true
        }

        override fun onRotate(detector: RotateGestureDetector): Boolean {
            mChild?.apply {
                val rotation = this.rotation + detector.degree * speed.value
                setRotation(rotation)
                rotationX = 0F
                rotationY = 0F
            }
            return true
        }
    }

    private val mSimpleGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return true
        }
    }

    private val mScaleGestureDetector = ScaleGestureDetector(context, mScaleListener)
    private val mTransferGestureDetector = GestureDetector(context, mTransferListener)
    private val mRotateGestureDetector = RotateGestureDetector(mRotateListener)
    private val mNormalGestureDetector = GestureDetector(context, mSimpleGestureListener)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val rootView = LayoutInflater.from(context).inflate(R.layout.gesture_layout, this, false)
        mResetButton = rootView.findViewById(R.id.reset_btn)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val count = event.pointerCount
        if (action == MotionEvent.ACTION_POINTER_DOWN && count == 2 && !mIsResizing) {
            mIsResizing = true
        }
        if (mIsResizing) {
            if (count == 2) {
                mScaleGestureDetector.onTouchEvent(event)
                mTransferGestureDetector.onTouchEvent(event)
                mRotateGestureDetector.onTouchEvent(event)
            }
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || count < 2) {
                mIsResizing = false
            }
        }
        if (!mTouchHandled && action == MotionEvent.ACTION_DOWN) {
            mNormalGestureDetector.onTouchEvent(event)
        }
        mTouchHandled = action == MotionEvent.ACTION_DOWN || mNormalGestureDetector.onTouchEvent(event)
        return mTouchHandled
    }

    override fun onInterceptTouchEvent(ev: MotionEvent) = ev.actionMasked == MotionEvent.ACTION_MOVE && mTouchHandled

}