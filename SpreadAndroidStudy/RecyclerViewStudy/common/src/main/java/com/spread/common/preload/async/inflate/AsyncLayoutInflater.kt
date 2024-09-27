package com.spread.common.preload.async.inflate

import android.content.Context
import android.os.Process
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.core.util.Pools
import com.spread.common.preload.async.data.PreloadViewInfo
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit


class AsyncLayoutInflater(
    context: Context?
) {

    val realInflater: LayoutInflater

    init {
        realInflater = BasicInflater(context)
    }

    @UiThread
    fun inflate(info: PreloadViewInfo, parent: ViewGroup?, callback: OnInflateFinishListener) {
        val request = InflateThread.obtainRequest().apply {
            this.inflater = this@AsyncLayoutInflater
            this.parent = parent
            this.info = info
            this.callback = callback
        }
        InflateThread.enqueue(request)
    }

    fun stopInflate() {
        InflateThread.interruptMe()
        InflateThread.interrupt()
        InflateThread.clear()
    }

    private object InflateThread : Thread() {
        @Volatile
        private var mShouldInterrupt = false

        private var mInflateSuccessCount = 0

        private var mTotalInflateCostTime = 0

        private val mQueue = ArrayBlockingQueue<InflateRequest>(20)

        private val mRequestPool = Pools.SynchronizedPool<InflateRequest>(20)

        init {
            Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT)
            start()
        }

        override fun run() {
            while (!mShouldInterrupt) {
                val request = mQueue.poll(1000, TimeUnit.MILLISECONDS) ?: break
                val info = request.info
                val inflater = request.inflater?.realInflater
                val view = if (info != null && inflater != null) {
                    inflater.inflate(info.layoutId, request.parent, false)
                } else {
                    null
                }
                mInflateSuccessCount++
                if (request.callback != null) {
                    request.callback!!.onInflateFinished(
                        view,
                        request.info?.layoutId,
                        request.parent
                    )
                }
                request.apply {
                    this.callback = null
                    this.inflater = null
                    this.parent = null
                    this.info = null
                }
                mRequestPool.release(request)
            }
        }

        fun obtainRequest(): InflateRequest {
            var obj = mRequestPool.acquire()
            if (obj == null) {
                obj = InflateRequest()
            }
            return obj
        }

        fun enqueue(request: InflateRequest) {
            mQueue.put(request)
        }

        fun clear() {
            mQueue.clear()
        }

        fun callbackLastRequest() {
            if (mQueue.isEmpty()) {
                return
            }
            for (request in mQueue) {
                request?.info?.desc?.let { request.callback?.onInflateError(it) }
            }
        }

        fun interruptMe() {
            mShouldInterrupt = true
        }
    }

    private data class InflateRequest(
        var inflater: AsyncLayoutInflater? = null,
        var parent: ViewGroup? = null,
        var info: PreloadViewInfo? = null,
        var callback: OnInflateFinishListener? = null
    )
}

