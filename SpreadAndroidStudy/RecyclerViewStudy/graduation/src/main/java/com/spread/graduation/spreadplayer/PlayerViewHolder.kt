package com.spread.graduation.spreadplayer

import android.graphics.Bitmap
import androidx.annotation.OptIn
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import com.spread.graduation.customui.MyVideoView

@OptIn(UnstableApi::class)
class PlayerViewHolder(
    private val playerView: MyVideoView,
    private val mAdapter: PlayAdapter
) : RecyclerView.ViewHolder(playerView) {

    init {
        playerView.addStateListener {
            onPlay {
                mAdapter.preloadOnceManually()
            }
        }
    }

    /**
     * 将当前ViewHolder链接到播放器上，同时断开其它所有ViewHolder和播放器的链接。
     */
    fun connectPlayer() {
        playerView.preemptPlayer()
    }

    fun play() {
        playerView.play()
    }

    fun setPlayItem(item: PlayItem) {
        playerView.setPlayItem(item)
    }

    fun onRecycled() {
        playerView.clearFlags()
        playerView.clearCover()
    }

    fun resizeVideo(newSize: VideoSize) {
        playerView.tryResizeVideo(newSize)
    }

    fun setVideoCover(bitmap: Bitmap) {
        playerView.setCover(bitmap)
    }
}