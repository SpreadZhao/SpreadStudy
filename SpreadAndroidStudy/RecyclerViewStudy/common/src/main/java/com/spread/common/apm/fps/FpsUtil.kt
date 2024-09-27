package com.spread.common.apm.fps

import android.app.Activity
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.FrameMetrics
import android.view.Window
import android.view.Window.OnFrameMetricsAvailableListener

object FpsUtil {

    private const val TAG = "FpsUtil"

    private val fpsInfoMap = mutableMapOf<Activity, FpsInfo>()
    private val fpsThread = HandlerThread("fps_thread")
    private val fpsHandler: Handler

    init {
        fpsThread.start()
        fpsHandler = Handler(fpsThread.looper)
    }

    /**
     * 检测的并不是丢帧率，而是在所有的Frame中，耗时过长的Frame的占比。
     * 60fps，耗时超过16ms为耗时过长。
     */
    private class OnFpsListener(private val activity: Activity) : OnFrameMetricsAvailableListener {

        private val refreshRate: Float
            get() = activity.window.context.display?.refreshRate ?: 60F

        private var tripleBuffered = false
        private var totalFrameCount = 0L
        private var longFrameCount = 0L
        private var longFrameRatio = 0.0

        override fun onFrameMetricsAvailable(
            window: Window?,
            frameMetrics: FrameMetrics?,
            dropCountSinceLastInvocation: Int
        ) {
            val fm = FrameMetrics(frameMetrics)
            if (fm.isFirstFrame) {
                Log.i(TAG, "First frame which we won't care for now.")
                return
            }
            totalFrameCount++
            val duration = fm.totalDuration.ms
            val interval = fm.deadline.ms
            if (!tripleBuffered) {
                if (duration > interval) {
                    tripleBuffered = true
                    longFrameCount++
                }
            } else {
                if (duration > 2 * interval) {
                    tripleBuffered = false
                    longFrameCount++
                }
            }
            val newFrameLostRate = longFrameCount.toDouble() / totalFrameCount
            if (longFrameRatio != newFrameLostRate) {
                Log.d(
                    TAG,
                    "longFrameRatio: ${newFrameLostRate * 100}%, long: $longFrameCount, total: $totalFrameCount"
                )
                longFrameRatio = newFrameLostRate
            }
        }

        private fun legacy(
            window: Window?,
            frameMetrics: FrameMetrics?,
            dropCountSinceLastInvocation: Int
        ) {
            if (frameMetrics?.isFirstFrame == true) {
                return
            }
            val info = fpsInfoMap[activity] ?: return
            val copy = FrameMetrics(frameMetrics)
            val duration = copy.getMetric(FrameMetrics.TOTAL_DURATION)
            val frameIntervalNanos = 1000000000 / (activity.display?.refreshRate ?: 60F)
            val calDroppedFrames = (duration / frameIntervalNanos).toInt()
            val instantFps = 1000000000 / duration

            val fps = if (instantFps < 60) {
                info.lostFrames++
                instantFps
            } else {
                60
            }
            info.totalFrames++
            val lost = info.lostFrames.toDouble() * 100 / info.totalFrames
            Log.d(
                "SpreadAPM",
                "Official: $dropCountSinceLastInvocation, my: $calDroppedFrames, fps: $fps, lost: $lost%, thread: ${Thread.currentThread().name}"
            )
        }
    }

    fun setFpsMonitor(activity: Activity) {
        if (!fpsInfoMap.contains(activity)) {
            fpsInfoMap[activity] = FpsInfo(0L, 0L)
        }
        activity.window.addOnFrameMetricsAvailableListener(OnFpsListener(activity), fpsHandler)
        Log.i(TAG, "FpsUtil registered!")
    }

    fun getInfo(activity: Activity) = fpsInfoMap[activity]
}