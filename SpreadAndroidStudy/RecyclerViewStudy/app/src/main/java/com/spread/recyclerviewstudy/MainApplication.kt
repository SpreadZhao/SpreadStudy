package com.spread.recyclerviewstudy

import android.app.Application
import com.spread.common.GlobalApplication

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalApplication.application = this
    }
}