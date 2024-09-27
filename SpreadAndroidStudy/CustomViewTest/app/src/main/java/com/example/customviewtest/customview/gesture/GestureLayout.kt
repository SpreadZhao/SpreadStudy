package com.example.customviewtest.customview.gesture

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

open class GestureLayout : RelativeLayout {

    interface Callback {
        fun handleResetBtnClick()
        val isFullScreen: Boolean
        val isResizable: Boolean
        val isTouchEnable: Boolean
        fun onActionUpCancel()
        fun resetScreen()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    open val isResized: Boolean get() = false

    open fun setGestureCallback(callback: Callback) {}

}