package com.spread.common.apm.fps

import android.view.FrameMetrics

val FrameMetrics.isFirstFrame: Boolean
    get() = getMetric(FrameMetrics.FIRST_DRAW_FRAME) == 1L

val FrameMetrics.unknownDelayDuration: Long
    get() = getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION)

val FrameMetrics.inputHandlingDuration: Long
    get() = getMetric(FrameMetrics.INPUT_HANDLING_DURATION)

val FrameMetrics.animationDuration: Long
    get() = getMetric(FrameMetrics.ANIMATION_DURATION)

val FrameMetrics.layoutMeasureDuration: Long
    get() = getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)

val FrameMetrics.drawDuration: Long
    get() = getMetric(FrameMetrics.DRAW_DURATION)

val FrameMetrics.syncDuration: Long
    get() = getMetric(FrameMetrics.SYNC_DURATION)

val FrameMetrics.commandIssueDuration: Long
    get() = getMetric(FrameMetrics.COMMAND_ISSUE_DURATION)

val FrameMetrics.swapBuffersDuration: Long
    get() = getMetric(FrameMetrics.SWAP_BUFFERS_DURATION)

val FrameMetrics.gpuDuration: Long
    get() = getMetric(FrameMetrics.GPU_DURATION)

val FrameMetrics.totalDuration: Long
    get() = getMetric(FrameMetrics.TOTAL_DURATION)

val FrameMetrics.intendedVsyncTime: Long
    get() = getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP)

val FrameMetrics.vsyncTime: Long
    get() = getMetric(FrameMetrics.VSYNC_TIMESTAMP)

val FrameMetrics.deadline: Long
    get() = getMetric(FrameMetrics.DEADLINE)

val FrameMetrics.infoMS: String
    get() {
        val builder = StringBuilder().apply {
            appendLine("isFirstFrame: $isFirstFrame")
            appendLine("render delay time: ${unknownDelayDuration.msStr}")
            appendLine("input handling time: ${inputHandlingDuration.msStr}")
            appendLine("animation time: ${animationDuration.msStr}")
            appendLine("layout & measure time: ${layoutMeasureDuration.msStr}")
            appendLine("draw time: ${drawDuration.msStr}")
            appendLine("sync time: ${syncDuration.msStr}")
            appendLine("command issue time: ${commandIssueDuration.msStr}")
            appendLine("swap buffers time: ${swapBuffersDuration.msStr}")
            appendLine("GPU time: ${gpuDuration.msStr}")
            appendLine("total time: ${totalDuration.msStr}")
            appendLine("intended vsync time: ${intendedVsyncTime.msStr}")
            appendLine("vsync time: ${vsyncTime.msStr}")
            appendLine("deadline: ${deadline.msStr}")
            appendLine("Print thread: ${Thread.currentThread().name}")
        }
        return builder.toString()
    }

val FrameMetrics.info: String
    get() {
        val builder = StringBuilder().apply {
            appendLine("isFirstFrame: $isFirstFrame")
            appendLine("render delay time: $unknownDelayDuration")
            appendLine("input handling time: $inputHandlingDuration")
            appendLine("animation time: $animationDuration")
            appendLine("layout & measure time: $layoutMeasureDuration")
            appendLine("draw time: $drawDuration")
            appendLine("sync time: $syncDuration")
            appendLine("command issue time: $commandIssueDuration")
            appendLine("swap buffers time: $swapBuffersDuration")
            appendLine("GPU time: $gpuDuration")
            appendLine("total time: $totalDuration")
            appendLine("intended vsync time: $intendedVsyncTime")
            appendLine("vsync time: $vsyncTime")
            appendLine("deadline: $deadline")
            appendLine("Print thread: ${Thread.currentThread().name}")
        }
        return builder.toString()
    }

val Long.ms: Long
    get() = this / 1000000

private val Long.msStr: String
    get() = this.ms.toString() + "ms"