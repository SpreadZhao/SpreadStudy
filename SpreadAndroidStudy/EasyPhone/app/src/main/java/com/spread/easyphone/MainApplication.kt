package com.spread.easyphone

import android.app.Application

class MainApplication : Application() {

    companion object {
        lateinit var applicationContext: Application
    }

    init {
        Companion.applicationContext = this
    }

}