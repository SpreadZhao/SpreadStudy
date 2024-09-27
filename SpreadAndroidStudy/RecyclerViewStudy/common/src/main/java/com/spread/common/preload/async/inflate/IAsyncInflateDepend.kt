package com.spread.common.preload.async.inflate

import android.view.LayoutInflater.Factory2

interface IAsyncInflateDepend {
    val layoutInflaterFactory: Factory2
    val themeResId: Int
}