package com.spread.graduation.spreadplayer

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.spread.common.apm.fps.FpsUtil
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.graduation.R

class CustomPlayTestActivity : AppCompatActivity() {

    private lateinit var playRV: PlayRecyclerView
    private lateinit var adapter: PlayAdapter
    private lateinit var layoutManager: PreloadLinearLayoutManager
    private var resumed = false

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == ComponentActivity.RESULT_OK
                && hasExternalStorageManager && !resumed
            ) {
                prepareForPlayView()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_play_test)
        playRV = findViewById(R.id.play_rv)
        prepare()

    }

    private fun prepare() {
        val dataSource = VideoDataSource()
        VideoDataSource.getLocalVideos()?.let {
            dataSource.addPlayItems(it)
        }
        adapter = PlayAdapter(dataSource, playRV)
        layoutManager = PreloadLinearLayoutManager(
            this,
            PreloadLinearLayoutManager.PreloadByOutside.WithoutTimeout
        )
        PlayerController.initPlayer(this)
    }

    private fun prepareForPlayView() {
        playRV.layoutManager = layoutManager
        playRV.adapter = adapter
    }

    private val hasExternalStorageManager: Boolean
        get() = Environment.isExternalStorageManager()

    private fun checkAuthority() {
        register.launch(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
    }

    private fun showAllFileManageDialog() {
        AlertDialog.Builder(this)
            .setTitle("No file access permission!")
            .setPositiveButton("Request") { _, _ ->
                checkAuthority()
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    override fun onStart() {
        super.onStart()
        FpsUtil.setFpsMonitor(this)
    }

    override fun onResume() {
        super.onResume()
        if (hasExternalStorageManager) {
            prepareForPlayView()
            resumed = true
        } else {
            showAllFileManageDialog()
        }
    }

    override fun onStop() {
        PlayerController.player?.stop()
        super.onStop()
    }
}