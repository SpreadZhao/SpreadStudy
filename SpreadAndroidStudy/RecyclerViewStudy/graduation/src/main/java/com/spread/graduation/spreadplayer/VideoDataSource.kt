package com.spread.graduation.spreadplayer

import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import java.io.File

/**
 * plan: player里面只允许有一个视频。视频的列表修改，播放控制，由外部类管理。
 */
class VideoDataSource {

    private val playItems = mutableListOf<PlayItem>()

    companion object {
        @JvmStatic
        fun getLocalVideos(): List<File>? {
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
            return target?.listFiles()?.toList()
        }
    }

    operator fun get(i: Int): PlayItem {
        return playItems[i]
    }

    fun addPlayItem(item: PlayItem) {
        playItems.add(item)
    }

    fun addPlayItem(item: MediaItem, path: String) {
        addPlayItem(PlayItem(item, path))
    }

    fun addPlayItem(item: Uri) {
        addPlayItem(MediaItem.fromUri(item), item.path ?: "")
    }

    fun addPlayItem(item: File) {
        addPlayItem(Uri.fromFile(item))
    }

    fun addPlayItems(items: List<File>) {
        val uris = items.map { it.toUri() }
        val paths = items.map { it.absolutePath }
        val mediaItems = uris.map { MediaItem.fromUri(it) }
        for (i in uris.indices) {
            playItems.add(PlayItem(mediaItems[i], paths[i]))
        }
    }

    val playingItem: PlayItem?
        get() {
            for (item in playItems) {
                if (item.isPlaying) {
                    return item
                }
            }
            return null
        }

    val playingItemIndex: Int
        get() {
            for (i in playItems.indices) {
                if (playItems[i].isPlaying) {
                    return i
                }
            }
            return -1
        }

    val size: Int
        get() = playItems.size

    fun isItemExists(position: Int) = position in playItems.indices

    fun markVideoPlaying(position: Int) {
        if (position !in playItems.indices) {
            return
        }
        val item = playItems[position]
        if (!item.isPlaying) {
            item.isPlaying = true
        }
    }

    fun markVideoNotPlaying(position: Int) {
        if (position !in playItems.indices) {
            return
        }
        val item = playItems[position]
        if (item.isPlaying) {
            item.isPlaying = false
        }
    }
}