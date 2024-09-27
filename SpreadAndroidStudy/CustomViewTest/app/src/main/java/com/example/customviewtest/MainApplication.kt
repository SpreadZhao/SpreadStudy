package com.example.customviewtest

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class MainApplication : Application() {

//    lateinit var window: TimeWindow

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
//        window = TimeWindow(this)
//        BlockCanary.install(this, AppBlockCanaryContext()).start()
    }
}