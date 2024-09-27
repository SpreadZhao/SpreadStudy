package com.spread.graduation.tryfirst

import android.util.Log
import android.util.SparseArray
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.core.util.forEach
import androidx.core.util.size
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class PlayerViewAdapter(
    private val player: Player
) : RecyclerView.Adapter<PlayerViewHolder>() {

    private val mPlayerViews = SparseArray<PlayerView>()

    private var mRecyclerView: PlayerViewRV? = null

    private var firstTime = true

    private var currHolder: PlayerViewHolder? = null

    init {
        player.addListener(object : Player.Listener {
            //            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
//                if (mRecyclerView == null || mediaItem == null) {
//                    return
//                }
//                if (player.getMediaItemAt(mRecyclerView!!.currPosition) == mediaItem) {
//                    mRecyclerView!!.currHolder?.hideOverlay()
//                }
////            }
//            override fun onPlaybackStateChanged(playbackState: Int) {
//                if (playbackState == Player.STATE_READY)
//            }
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    mRecyclerView?.currHolder?.hideOverlay()
                }
            }
        })
    }

    @OptIn(UnstableApi::class)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val defaultLP = ViewGroup.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        val playerView = PlayerView(parent.context).apply {
            layoutParams = defaultLP
            setShowNextButton(false)
            setShowPreviousButton(false)
        }
        val textView = TextView(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            textSize = 100f
        }
        return PlayerViewHolder(playerView, textView).apply { addOverlay() }
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
//        player.seekToNext()
        Log.d("SpreadVideo", "bind position: $position")
        if (!mPlayerViews.contains(position)) {
            mPlayerViews[position] = holder.playerView
        }
        if (firstTime) {
            mRecyclerView?.initHolder(holder)
            firstTime = false
        }
        holder.bindOverlay(position)
    }

    fun setRecyclerView(rv: PlayerViewRV) {
        mRecyclerView = rv
    }

    fun detachPlayerBut(exclude: Int) {
        mPlayerViews.forEach { key, value ->
            if (key != exclude) {
                value.player = null
            }
        }
    }

    fun getPlayerView(position: Int): PlayerView? {
        if (position in 0..<mPlayerViews.size) {
            return mPlayerViews[position]
        }
        return null
    }

    fun updateCurrHolder(holder: PlayerViewHolder) {
        currHolder = holder
    }

    override fun getItemCount(): Int {
        return player.mediaItemCount
    }

    override fun onViewRecycled(holder: PlayerViewHolder) {
        holder.playerView.player = null
        super.onViewRecycled(holder)
    }

    private fun findMediaItemPosition(item: MediaItem): Int {
        for (i in 0..<player.mediaItemCount) {
            val playItem = player.getMediaItemAt(i)
            if (item == playItem) {
                return i
            }
        }
        return -1
    }
}