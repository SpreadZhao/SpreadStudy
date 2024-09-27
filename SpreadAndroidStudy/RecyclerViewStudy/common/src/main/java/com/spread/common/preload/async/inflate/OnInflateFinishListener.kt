package com.spread.common.preload.async.inflate

import android.view.View
import android.view.ViewGroup

interface OnInflateFinishListener {
    fun onInflateFinished(view: View?, resId: Int?, parent: ViewGroup?)
    fun onInflateError(desc: String)
}