package com.spread.recyclerviewstudy.tool.apm

import android.view.Display
import android.view.Window

interface IFrameTracer {
    var totalFrames: Long
    var lostFrames: Long
    val mWindow: Window
    val mDisplay: Display?
}