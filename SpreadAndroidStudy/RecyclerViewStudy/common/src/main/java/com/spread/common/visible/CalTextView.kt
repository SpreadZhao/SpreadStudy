package com.spread.common.visible

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CalTextView : AppCompatTextView, VisibleChecker.IViewChecked {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun validArea(): VisibleChecker.Area {
        val origin = IntArray(2)
//        val bounds = Rect()
//        paint.getTextBounds(text, 0, text.length, bounds)
        getLocationOnScreen(origin)
        return VisibleChecker.Area(
            VisibleChecker.Point(origin[0], origin[1]),
            measuredWidth,
            measuredHeight
        )
    }

}