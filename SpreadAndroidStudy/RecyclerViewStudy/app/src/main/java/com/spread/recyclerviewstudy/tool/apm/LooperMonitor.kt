package com.spread.recyclerviewstudy.tool.apm

import android.util.Log
import android.util.Printer

object LooperMonitor {
    private val sPrinter = Printer {
        if (it.isEmpty()) {
            return@Printer
        }
        Log.d("SpreadAPM", "printer: $it")
    }
}