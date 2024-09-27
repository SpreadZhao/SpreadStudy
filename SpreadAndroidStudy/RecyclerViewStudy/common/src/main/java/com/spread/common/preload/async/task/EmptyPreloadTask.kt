package com.spread.common.preload.async.task

import android.content.Context

abstract class EmptyPreloadTask(
    context: Context,
    frontTask: PreloadTask<*>
) : PreloadTask<Empty>(context, frontTask)