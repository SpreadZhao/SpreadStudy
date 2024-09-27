package com.spread.common.preload.async.inflate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

class BasicInflater(context: Context?) : LayoutInflater(context) {
    override fun cloneInContext(newContext: Context?): LayoutInflater {
        return BasicInflater(newContext)
    }

    override fun onCreateView(
        name: String,
        attrs: AttributeSet?
    ): View? {
        return super.onCreateView(name, attrs)
    }
}