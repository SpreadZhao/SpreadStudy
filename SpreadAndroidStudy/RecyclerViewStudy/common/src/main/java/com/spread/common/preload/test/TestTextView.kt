package com.spread.common.preload.test

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

class TestTextView : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    /**
     * 如果这里返回true，代表子View消费了触摸事件。这个时候如果没有判断DRAGGING，就会出现
     * 回收的bug。
     *
     * 问题的本质是，如果这里消费了ACTION_DOWN，那么RV的intercept中就会拦截到
     * ACTION_MOVE，从而设置成DRAGGING并拦截事件，当前View收到CANCEL；
     *
     * 如果这里没有消费ACTION_DOWN，那么会往上传递，也就是RV消费了ACTION_DOWN。
     * 那么之后RV的拦截就不会再执行了，所以就不会在intercept里设置DRAGGING，
     * 转而在onTouchEvent里设置DRAGGING，这样之后的滚动就是一个同步的过程，
     * 也就不会出现被外界requestLayout()插入的问题了。
     *
     * TODO: Things above should appear in my paper later.
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("TestTextView", "ViewItem handle ${event?.actionStr()}[${event?.hashCode()}]")
        return super.onTouchEvent(event)
//        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//    Thread.sleep(10)
    }
}