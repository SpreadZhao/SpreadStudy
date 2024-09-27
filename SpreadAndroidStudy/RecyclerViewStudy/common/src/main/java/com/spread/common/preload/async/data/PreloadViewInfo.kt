package com.spread.common.preload.async.data

class PreloadViewInfo private constructor(builder: Builder) {

    var layoutId = -1
    var preloadCount = 0
    var desc = ""

    init {
        this.layoutId = builder.mLayoutId
        this.preloadCount = builder.mPreloadCount
        this.desc = builder.mDesc
    }

    object Builder {
        var mLayoutId = -1
        var mPreloadCount = 0
        var mDesc = ""

        fun build(): PreloadViewInfo {
            return PreloadViewInfo(this)
        }
    }


}