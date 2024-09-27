package com.spread.common.preload.async.inflate

import android.content.Context
import com.spread.common.preload.async.data.PreloadViewInfo

/**
 * 暴露给task的服务，不暴露[AsyncLayoutInflateManager]本身
 */
interface IAsyncLayoutInflateService {
    val context: Context
    fun inflate(info: PreloadViewInfo, callback: OnInflateFinishListener)
}