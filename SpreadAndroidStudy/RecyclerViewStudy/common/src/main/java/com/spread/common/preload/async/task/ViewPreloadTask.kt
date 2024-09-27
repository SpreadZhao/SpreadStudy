package com.spread.common.preload.async.task

import android.content.Context
import android.view.View
import com.spread.common.preload.async.data.PreloadRunningTime
import com.spread.common.preload.async.data.PreloadType

open class ViewPreloadTask(context: Context, frontTask: PreloadTask<*>) :
    PreloadTask<View>(context, frontTask) {

    override val preloadTaskRunningTime: PreloadRunningTime
        get() = TODO("Not yet implemented")

    override val preloadType: PreloadType
        get() = TODO("Not yet implemented")

    override fun run(): View {
        TODO("Not yet implemented")
    }
}