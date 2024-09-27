package com.spread.common.preload.prebind

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.util.Printer
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.preload.test.actionStr

open class LogRecyclerView : RecyclerView {

    companion object {
        private const val TAG = "LogRecyclerView"
    }

    private val mPrinter = Printer {
        Log.d(TAG, it)
    }

    override fun onScrollStateChanged(state: Int) {
        if (state == SCROLL_STATE_DRAGGING) {
            Log.i(TAG, "RV scroll state to dragging!")
        }
        Looper.getMainLooper().dump(mPrinter, "RV")
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        Log.d(TAG, "RV onInterceptTouchEvent: ${e?.actionStr()}")
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        Log.d(TAG, "RV onTouchEvent: ${e?.actionStr()}")
        return super.onTouchEvent(e)
    }

    override fun onScrolled(dx: Int, dy: Int) {
        Log.i(TAG, "RV onScrolled! dx: $dx, dy: $dy")
        Looper.getMainLooper().dump(mPrinter, "RV")
        super.onScrolled(dx, dy)
    }
}