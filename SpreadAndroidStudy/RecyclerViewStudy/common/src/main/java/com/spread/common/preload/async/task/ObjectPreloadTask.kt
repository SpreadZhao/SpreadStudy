package com.spread.common.preload.async.task

import android.content.Context

abstract class AnyPreloadTask(
    context: Context,
    frontTask: PreloadTask<*>
) : PreloadTask<Any>(context, frontTask)