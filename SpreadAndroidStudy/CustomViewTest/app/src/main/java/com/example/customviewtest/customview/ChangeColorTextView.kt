package com.example.customviewtest.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.customviewtest.utils.sp
import kotlin.math.abs

class ChangeColorTextView : View {

    var text: String = ""
    var color = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }
    private val mPaint = Paint()
    private var mBounds = Rect()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        mPaint.textSize = 14.sp
        mPaint.color = color
        mPaint.isAntiAlias = true
    }

    private val currTextHeight: Float
        get() {
            val metrics = mPaint.fontMetrics
            return abs(metrics.ascent - metrics.descent)
        }

    // 基线位置的偏移量，用来计算Canvas画图时的纵坐标
    private val baselineOffset: Float
        get() {
            val metrics = mPaint.fontMetrics
            return (currTextHeight - metrics.bottom + metrics.top) / 2 - metrics.top
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        mPaint.getTextBounds(text, 0, text.length, mBounds)
        if (heightMode == MeasureSpec.AT_MOST && widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(paddingStart + mBounds.width() + paddingEnd, (paddingTop + currTextHeight + paddingBottom).toInt())
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = color
        val startX = paddingStart.toFloat()
        canvas.drawText(text, startX, paddingTop + baselineOffset, mPaint)
    }


}