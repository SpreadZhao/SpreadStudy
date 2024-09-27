package com.spread.common.apm.fps

import android.util.Log

object TimeCostUtil {

    private const val TAG = "TimeCostUtil"

    private var time = 0L
    private var tag = ""

    fun start(tag: String) {
        time = System.currentTimeMillis()
        this.tag = tag
    }

    fun finish() {
        val finishTime = System.currentTimeMillis()
        Log.i(TAG, "$tag cost time: ${finishTime - time}ms")
        time = 0L
    }
}