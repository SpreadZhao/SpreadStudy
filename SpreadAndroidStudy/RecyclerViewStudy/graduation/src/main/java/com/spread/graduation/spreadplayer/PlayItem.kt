package com.spread.graduation.spreadplayer

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.media3.common.MediaItem
import java.util.concurrent.Executors

/**
 * MediaItem的封装，包含刷视频相关的业务逻辑
 */
class PlayItem(val mediaItem: MediaItem, filePath: String) {
    var isPlaying = false
    var width: Int
    var height: Int
    var title: String
    private val retriever = MediaMetadataRetriever()
    var firstFrame: Bitmap? = null

    companion object {
        private val threadPool = Executors.newCachedThreadPool()
    }

    init {
        retriever.setDataSource(filePath)
        width =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toInt() ?: 0
        height =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toInt()
                ?: 0
        title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE).toString()
        threadPool.execute {
            firstFrame = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        }
    }
}