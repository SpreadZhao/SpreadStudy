package com.example.picphone.shake

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.picphone.settings.SettingsActivity

class ShakeOpenSettings(
    private val fromActivity: Activity
) : IShakeListener {

    private var vibrator: Vibrator? = null

    companion object {
        private const val TAG = "ShakeDetector"
        private const val START_ACTIVITY_THRESHOLD = 3
    }

    fun setVibrator(vibrator: Vibrator?) {
        Log.d(TAG, "setVibrator called")
        if (this.vibrator == null) {
            this.vibrator = vibrator
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun configVibrator(vm: VibratorManager?) {
        Log.d(TAG, "configVibrator called")
        if (this.vibrator == null) {
            this.vibrator = vm?.defaultVibrator
        }
    }


    override fun onShake(count: Int) {
        Log.d(TAG, "you shook")
        if (count <= START_ACTIVITY_THRESHOLD) {
            val v = this.vibrator
            if (v != null && v.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    v.vibrate(longArrayOf(100, 100), 0)
                }
            }
            Log.d(TAG, "count: $count")
            return
        }
        // start activity
        val intent = Intent(fromActivity, SettingsActivity::class.java)
        fromActivity.startActivity(intent)
    }
}