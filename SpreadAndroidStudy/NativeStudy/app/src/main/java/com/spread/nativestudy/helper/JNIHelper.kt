package com.spread.nativestudy.helper

import android.os.Build
import android.util.Log
import androidx.annotation.Keep

class JNIHelper {
    @Keep
    private fun updateStatus(msg: String) {
        if (msg.lowercase().contains("error")) {
            Log.e("JNIHandler", "Native Err: $msg");
        } else {
            Log.i("JNIHandler", "Native Msg: $msg")
        }
    }

    companion object {
        @Keep
        @JvmStatic
        fun getBuildVersion() = Build.VERSION.RELEASE
    }

    @Keep
    fun getRuntimeFreeMemory() = Runtime.getRuntime().freeMemory()
}