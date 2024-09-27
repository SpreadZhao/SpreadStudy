package com.spread.graduation.spreadplayer

import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout.LayoutParams
import androidx.annotation.OptIn
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.graduation.customui.MyVideoView
import java.util.concurrent.TimeUnit

@OptIn(UnstableApi::class)
class PlayAdapter(
    private val dataSource: VideoDataSource,
    private val recyclerView: PlayRecyclerView
) : Adapter<PlayerViewHolder>() {

    private var currHolder: PlayerViewHolder? = null
    private var isInit = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val videoView = MyVideoView(parent.context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }
        return PlayerViewHolder(videoView, this)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
//        Debug.startMethodTracing("bind$position")
        val playItem = dataSource[position]
        playItem.firstFrame?.let {
            holder.setVideoCover(it)
        }
        val size = VideoSize(playItem.width, playItem.height)
        holder.resizeVideo(size)
        if (position == 0 && !isInit) {
            currHolder = holder
            onHolderSelect(position)
            isInit = true
        }
        Log.d(
            "Spread-Play",
            "bind[${holder.hashCode()}] position: $position, title: ${playItem.title}"
        )
        // Test if pre-bind is useful
        TimeUnit.MILLISECONDS.sleep(160)
//        Debug.stopMethodTracing()
    }

    fun onHolderSelect(position: Int) {
        if (!dataSource.isItemExists(position)) {
            return
        }
        val holder = recyclerView.findViewHolderForAdapterPosition(position)
        if (holder != null && holder is PlayerViewHolder) {
            currHolder = holder
        }

        val playItem = dataSource[position]
        currHolder?.setPlayItem(playItem)
        currHolder?.connectPlayer()
        currHolder?.play()
    }

    fun preloadOnceManually() {
        (recyclerView.layoutManager as? PreloadLinearLayoutManager)?.preloadOnce()
    }

    override fun onViewRecycled(holder: PlayerViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    override fun getItemCount() = dataSource.size
}