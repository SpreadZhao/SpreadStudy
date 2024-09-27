package com.spread.common.tool

import android.content.Context
import android.graphics.Rect
import android.view.WindowManager

object UITools {

    private var sRealSize: Rect? = null

    fun getRealScreenHeight(context: Context): Int {
        val bounds = getRealDisplaySize(context)
        return bounds.height()
    }

    fun getRealScreenWidth(context: Context): Int {
        val bounds = getRealDisplaySize(context)
        return bounds.width()
    }

    // https://developer.android.com/about/versions/12/deprecations
    // TODO: so, what is metrics?
    private fun getRealDisplaySize(context: Context): Rect {
        if (sRealSize != null) {
            return sRealSize!!
        }
        val display = context.display ?: return Rect()
        val windowContext =
            context.createWindowContext(display, WindowManager.LayoutParams.TYPE_APPLICATION, null)
        val metrics = windowContext.getSystemService(WindowManager::class.java).currentWindowMetrics
        return metrics.bounds.apply { sRealSize = this }
    }
}