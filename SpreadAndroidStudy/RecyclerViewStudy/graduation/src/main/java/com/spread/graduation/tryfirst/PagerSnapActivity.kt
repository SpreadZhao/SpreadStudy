package com.spread.graduation.tryfirst

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.HandlerThread
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.Player
import com.spread.common.apm.fps.FpsUtil
import com.spread.graduation.R

class PagerSnapActivity : AppCompatActivity() {

    private lateinit var prv: PlayerViewRV

    private val fpsThread = HandlerThread("fps")

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == ComponentActivity.RESULT_OK
                && hasExternalStorageManager
            ) {
                doPlay()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_snap)
//        rv = findViewById(R.id.pager_snap_rv)
        prv = findViewById(R.id.player_view_recyclerview)
        fpsThread.start()
    }

    override fun onResume() {
        super.onResume()
        FpsUtil.setFpsMonitor(this)
        if (!hasExternalStorageManager) {
            showAllFileManageDialog()
        }
    }

    private fun checkAuthority() {

        register.launch(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
    }

    private fun doPlay() {
        if (prv.playerState == Player.STATE_IDLE) {
            prv.prepare()
        }
        prv.play()
    }

    private val hasExternalStorageManager: Boolean
        get() = Environment.isExternalStorageManager()

    private fun showAllFileManageDialog() {
        AlertDialog.Builder(this)
            .setTitle("No file access permission!")
            .setPositiveButton("Request") { _, _ ->
                checkAuthority()
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    override fun onStop() {
        prv.stop()
        super.onStop()
    }

    override fun onDestroy() {
        prv.release()
        super.onDestroy()
    }

}