package com.spread.graduation.spreadplayer

import android.content.Context
import com.spread.graduation.customui.MyVideoView

object PlayerController {

    var player: MyPlayer? = null
        private set

    fun initPlayer(context: Context) {
        if (player == null) {
            player = MyPlayer(context)
        }
    }

    fun switchVideoView(newView: MyVideoView) {
        player?.switchVideoView(newView)
    }
}