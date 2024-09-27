package com.spread.common.preload.async.task

import android.content.Context
import android.util.Log
import com.spread.common.preload.async.data.PreloadRunningTime
import com.spread.common.preload.async.data.PreloadType
import java.util.concurrent.CopyOnWriteArrayList

abstract class PreloadTask<T>(context: Context, frontTask: PreloadTask<*>) : IPreloadTask<T> {

    companion object {
        private const val TAG = "PreloadTask"
    }

    private val mPreloadValueList = CopyOnWriteArrayList<T>()

    private var mStarted = false

    private val mContext: Context = context

    private val mFrontTask: PreloadTask<*> = frontTask

    private var mNeedCheckContext = false

    abstract val preloadTaskRunningTime: PreloadRunningTime

    abstract val preloadType: PreloadType

    abstract fun run(): T

    val preloadValue: T
        get() {
            // TODO 预加载获取
            val value = mPreloadValueList.removeFirstOrNull()!!
            return value
        }

    override fun runWithAsync() {
        Log.d(TAG, "runWithAsync")
    }

    override fun runWithIdle() {
        Log.d(TAG, "runWithIdle")
    }

    override fun runWithSync(): T {
        Log.d(TAG, "runWithSync")
        return preloadValue
    }

    fun start() {
        mStarted = true
        when (preloadType) {
            PreloadType.ASYNC -> {}
            PreloadType.IDLE -> {}
            PreloadType.SYNC -> {}
        }
    }
}