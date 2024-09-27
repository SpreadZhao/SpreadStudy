package com.spread.recyclerviewstudy.tool.apm

import android.os.Looper
import android.util.Log
import android.util.Printer

class MainLooperMonitor {
    private val mMainLooper = Looper.getMainLooper()

    class LooperPrinter : Printer {
        override fun println(x: String?) {
            Log.d("SpreadAPM", "printer: $x")
        }
    }

    init {
        mMainLooper.setMessageLogging(LooperPrinter())
    }
}