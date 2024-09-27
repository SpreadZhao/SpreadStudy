package com.spread.graduation.tryfirst

import android.view.View
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class PlayerViewHolder(val playerView: PlayerView, private val placeHolderView: TextView) :
    RecyclerView.ViewHolder(playerView) {

    @OptIn(UnstableApi::class)
    fun replaceAnotherPlayerView(player: Player, another: PlayerView) {
        PlayerView.switchTargetView(player, another, playerView)
    }

    @OptIn(UnstableApi::class)
    fun addOverlay() {
        val overlayLayout = playerView.overlayFrameLayout ?: return
        overlayLayout.addView(placeHolderView)
    }

    fun bindOverlay(position: Int) {
        placeHolderView.text = position.toString()
        placeHolderView.visibility = View.VISIBLE
    }

    fun hideOverlay() {

        placeHolderView.postDelayed({
            placeHolderView.visibility = View.GONE
        }, 1000)
    }
}