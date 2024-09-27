package com.spread.graduation.spreadplayer

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.spread.graduation.customui.MyVideoView

class MyPlayer(context: Context) {

    private var mLastVideoView: MyVideoView? = null

    val realPlayer = ExoPlayer.Builder(context).build()

    fun switchVideoView(newView: MyVideoView) {
        if (mLastVideoView === newView) {
            return
        }
        newView.onConnectToPlayer(realPlayer)
        mLastVideoView?.onDisconnectToPlayer(realPlayer)
        mLastVideoView = newView
    }

    /**
     * 没设置PlayItem，根本没办法提前prepare。所以我们在不对播放器动刀的情况下，
     * 根本没办法在设置Item之前就对下一个视频prepare。也就是视频的离凭与渲染我们
     * 是做不了的。但是，我们可以看看能不能用另一种策略去加载视频。比如维护两个
     * PlayItem。
     */
    fun setPlayItem(item: PlayItem) {
        if (realPlayer.isCommandAvailable(Player.COMMAND_SET_MEDIA_ITEM)) {
            realPlayer.setMediaItem(item.mediaItem)
        }
        if (realPlayer.isCommandAvailable(Player.COMMAND_PREPARE)) {
            realPlayer.prepare()
        }
    }

    fun play() {
        realPlayer.play()
    }

    fun stop() {
        realPlayer.stop()
    }


}