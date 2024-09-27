package com.example.picphone.shake

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector : SensorEventListener {

    private var shakeListener: IShakeListener? = null
    private var shakeCount = 0

    companion object {
        private val SHAKE_THRESHOLD_GRAVITY: Float = 8f
        private val SHAKE_SLOP_TIME_MS: Int = 500
        private val SHAKE_COUNT_RESET_TIME_MS: Int = 3000
    }

    fun setShakeListener(shakeListener: IShakeListener) {
        this.shakeListener = shakeListener
    }

    fun clear() {
        this.shakeCount = 0
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val listener = this.shakeListener
        if (listener == null || event == null) {
            return
        }
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH
        val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)
        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            shakeCount++
            listener.onShake(shakeCount)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}