package com.spread.common.preload.async.inflate.demo

import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.asynclayoutinflater.view.AsyncLayoutInflater

object DefaultAsyncInflater : PreloadHelper.ILayoutInflater {

    private var mInflater: AsyncLayoutInflater? = null

    override fun asyncInflate(
        parent: ViewGroup,
        layoutId: Int,
        callback: PreloadHelper.InflateCallback
    ) {
        if (mInflater == null) {
            val context = parent.context
            mInflater =
                AsyncLayoutInflater(ContextThemeWrapper(context.applicationContext, context.theme))
        }
        mInflater!!.inflate(layoutId, parent) { view, resId, _ ->
            callback.onInflateFinished(resId, view)
        }
    }

    override fun syncInflate(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }
}