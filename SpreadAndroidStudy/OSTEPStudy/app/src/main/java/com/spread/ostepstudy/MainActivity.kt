package com.spread.ostepstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.spread.ostepstudy.chapter_activity.IntroductionActivity
import com.spread.ostepstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnIntro.setOnClickListener { naviToActivity(IntroductionActivity::class.java) }
    }

    private fun naviToActivity(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    /**
     * A native method that is implemented by the 'ostepstudy' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'ostepstudy' library on application startup.
        init {
            System.loadLibrary("ostepstudy")
        }
    }
}