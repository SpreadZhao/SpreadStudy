package com.spread.graduation.customui

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.Player.COMMAND_SET_VIDEO_SURFACE
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import com.spread.common.apm.fps.TimeCostUtil
import com.spread.common.tool.ImmersiveColor
import com.spread.graduation.spreadplayer.PlayItem
import com.spread.graduation.spreadplayer.PlayerController

@OptIn(UnstableApi::class)
class MyVideoView : FrameLayout {

    private val mContentFrame: AspectRatioFrameLayout
    private val mCoverFrame: AspectRatioFrameLayout
    private val backGround: ImageView
    private val mSurfaceView: SurfaceView
    private val playListener = PlayListener()
    private var videoSizeChanged = false
    private val stateListeners = mutableListOf<VideoStateListener>()

    class VideoStateListener {
        var onPlay: (() -> Unit)? = null
        fun onPlay(what: () -> Unit) {
            this.onPlay = what
        }
    }

    fun addStateListener(init: VideoStateListener.() -> Unit) {
        val listener = VideoStateListener()
        listener.init()
        stateListeners.add(listener)
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContentFrame = newDefaultAspectRatioFrameLayout()
        this.mCoverFrame = newDefaultAspectRatioFrameLayout()
        this.backGround = newDefaultImageView()
        mContentFrame.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        mCoverFrame.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        this.mSurfaceView = SurfaceView(context)
        mContentFrame.addView(mSurfaceView, 0)
        this.addView(backGround)
        this.addView(mContentFrame)
        this.addView(mCoverFrame)
        mCoverFrame.visibility = View.GONE
    }

    fun preemptPlayer() {
        PlayerController.switchVideoView(this)
    }

    fun setPlayItem(playItem: PlayItem) {
        PlayerController.player?.setPlayItem(playItem)
    }

    fun play() {
        PlayerController.player?.play()
    }

    fun stop() {
        PlayerController.player?.stop()
    }

    fun clearFlags() {
        videoSizeChanged = false
    }

    fun clearCover() {
        mCoverFrame.removeAllViews()
        mCoverFrame.visibility = View.GONE
    }

    fun tryResizeVideo(videoSize: VideoSize) {
        if (videoSizeChanged) {
            return
        }
        val width = videoSize.width
        val height = videoSize.height
        val ratio =
            if (width == 0 || height == 0) 0F else (width * videoSize.pixelWidthHeightRatio) / height
        mContentFrame.setAspectRatio(ratio)
        if (ratio != 0F) {
            videoSizeChanged = true
        }
    }

    fun onDisconnectToPlayer(player: Player) {
        player.removeListener(playListener)
        if (player.isCommandAvailable(COMMAND_SET_VIDEO_SURFACE)) {
            player.clearVideoSurfaceView(mSurfaceView)
        }
    }

    fun onConnectToPlayer(player: Player) {
        if (player.isCommandAvailable(COMMAND_SET_VIDEO_SURFACE)) {
            player.setVideoSurfaceView(mSurfaceView)
        }
        player.addListener(playListener)
    }

    fun setCover(bitmap: Bitmap) {
        val image = ImageView(context)
        image.setImageBitmap(bitmap)
        if (mCoverFrame.childCount == 0) {
            mCoverFrame.addView(image)
            image.bringToFront()
            mCoverFrame.visibility = View.VISIBLE
            newBackground(ImmersiveColor.fromBitmap(bitmap) ?: 0)
        }
    }

    private fun newBackground(color: Int) {
        backGround.setBackgroundColor(color)
    }

    private fun newDefaultAspectRatioFrameLayout(): AspectRatioFrameLayout {
        return AspectRatioFrameLayout(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )
        }
    }

    private fun newDefaultImageView(): ImageView {
        return ImageView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }
    }

    inner class PlayListener : Player.Listener {
        override fun onVideoSizeChanged(videoSize: VideoSize) {
            super.onVideoSizeChanged(videoSize)
            tryResizeVideo(videoSize)
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_READY) {
                clearCover()
                Log.i("Spread-Play", "起播啦！")
                for (listener in stateListeners) {
                    listener.onPlay?.invoke()
                }
                TimeCostUtil.finish()
            }
        }
    }

}