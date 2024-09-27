package com.spread.common.tool

import android.graphics.Bitmap
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target

object ImmersiveColor {

    /**
     * 该方法经常耗时100-200ms。因此要么异步，要么离屏预渲染
     */
    fun fromBitmap(bitmap: Bitmap): Int? {
        val builder = Palette.from(bitmap).clearTargets()
        return builder.addTarget(Target.DARK_MUTED).generate().darkMutedSwatch?.rgb
    }
}