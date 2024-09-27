package com.spread.graduation.tryfirst

import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class VideoPagerSnapHelper : PagerSnapHelper() {

    private val snapListeners = arrayListOf<SnapListener>()

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        dispatchOnSnapTo(position)
        return position
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        val view = super.findSnapView(layoutManager)
        if (view != null) {
            dispatchSnapView(view)
        }
        return view
    }

    private fun dispatchOnSnapTo(position: Int) {
        for (listener in snapListeners) {
            listener.onSnapToPosition(position)
        }
    }

    private fun dispatchSnapView(view: View) {
        for (listener in snapListeners) {
            listener.onFindSnapView(view)
        }
    }

    fun addSnapListener(listener: SnapListener) {
        if (!snapListeners.contains(listener)) {
            snapListeners.add(listener)
        }
    }

    fun removeSnapListener(listener: SnapListener) {
        if (snapListeners.contains(listener)) {
            snapListeners.remove(listener)
        }
    }

    interface SnapListener {
        fun onSnapToPosition(position: Int)

        fun onFindSnapView(view: View)
    }
}