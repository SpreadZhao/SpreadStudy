package com.spread.graduation.tryfirst

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.spread.common.preload.prebind.DispatcherRecyclerView
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import java.io.File

class PlayerViewRV : DispatcherRecyclerView {

    private val mPlayer: Player = ExoPlayer.Builder(context).build()
    private val mSnapHelper = VideoPagerSnapHelper()
    private var _currHolder: PlayerViewHolder? = null
    val currHolder get() = _currHolder
    private var _currPosition = 0
    val currPosition get() = _currPosition
    private var lastPosition = 0
    private val mAdapter = PlayerViewAdapter(mPlayer)
    private val mLayoutManager = PreloadLinearLayoutManager(context).apply {
        isItemPrefetchEnabled = false
//        enablePreload(true)
//        adjustPreloadTime(false)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, style: Int) : super(context, attrs, style)

    init {
        mLayoutManager.isItemPrefetchEnabled = false
        getPlayItems()?.forEach {
            val videoUri = Uri.fromFile(it)
            val playItem = MediaItem.fromUri(videoUri)
            mPlayer.addMediaItem(playItem)
        }
        this.layoutManager = mLayoutManager
        initAdapter(mAdapter)
        setItemViewCacheSize(0)
        setHasFixedSize(true)
        mSnapHelper.attachToRecyclerView(this)
        addSnapToListener()
    }

    fun initHolder(holder: PlayerViewHolder) {
        if (_currHolder == null) {
            holder.playerView.player = mPlayer
            mPlayer.seekTo(0, 0)
            mPlayer.play()
            _currHolder = holder
        }
    }

    private fun initAdapter(adapter: PlayerViewAdapter) {
        this.adapter = adapter
        adapter.setRecyclerView(this)
    }

    private fun addSnapToListener() {
        mSnapHelper.addSnapListener(object : VideoPagerSnapHelper.SnapListener {
            override fun onSnapToPosition(position: Int) {
                _currHolder = mAdapter.getPlayerView(position)
                    ?.let { getChildViewHolder(it) } as? PlayerViewHolder
                if (position == lastPosition) {
                    return
                }
                _currPosition = position
                _currHolder?.configAndReplace(position)
            }

            override fun onFindSnapView(view: View) {
                val targetPosition = mLayoutManager.getPosition(view)
                if (targetPosition == NO_POSITION || targetPosition == _currPosition || targetPosition == 0) {
                    return
                }
                _currHolder = mAdapter.getPlayerView(targetPosition)
                    ?.let { getChildViewHolder(it) } as? PlayerViewHolder
                _currPosition = targetPosition
                _currHolder?.configAndReplace(targetPosition)
            }
        })
    }

    private fun PlayerViewHolder.configAndReplace(position: Int) {
        val lastView = mAdapter.getPlayerView(lastPosition)
        if (lastView != null && lastView != this.playerView) {
            replaceAnotherPlayerView(mPlayer, lastView)
        }
        mPlayer.seekTo(position, 0)
        mPlayer.play()
        lastPosition = position
    }

    private fun getPlayItems(): Array<out File>? {
        val dir = Environment.getExternalStorageDirectory()
        val testDirs = dir.listFiles { file -> (file.name == "Test" && file.isDirectory) }
        val target = if (testDirs?.isNotEmpty() == true) {
            testDirs.first()
        } else {
            null
        }
        Log.d("SpreadVideo", "testDir find: ${target != null}")
        val uri = Uri.fromFile(target)
        Log.d("SpreadVideo", "target uri: $uri")
        return target?.listFiles()
    }

    val playerState
        get() = mPlayer.playbackState

    fun prepare() {
        mPlayer.prepare()
    }

    fun play() {
        mPlayer.play()
    }

    fun stop() {
        mPlayer.stop()
    }

    fun release() {
        mPlayer.release()
    }
}