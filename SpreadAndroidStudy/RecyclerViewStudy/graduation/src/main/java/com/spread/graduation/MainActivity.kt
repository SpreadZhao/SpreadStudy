package com.spread.graduation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.spread.graduation.network.NewsActivity
import com.spread.graduation.spreadplayer.CustomPlayTestActivity
import com.spread.graduation.tryfirst.PagerSnapActivity

class MainActivity : AppCompatActivity() {

    private val btns = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBtns()
    }

    private fun initBtns() {
        btns.add(R.id.pager_snap_rv, R.id.custom_play_test, R.id.news)
        setListeners()
    }

    private fun MutableList<Button>.add(vararg ids: Int) {
        for (id in ids) {
            add(findViewById(id))
        }
    }

    private fun setListeners() {
        btns.forEach { it.setOnClickListener(listener) }
    }

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.pager_snap_rv -> naviToActivity<PagerSnapActivity>()
            R.id.custom_play_test -> naviToActivity<CustomPlayTestActivity>()
            R.id.news -> naviToActivity<NewsActivity>()
        }
    }

    private fun <T> naviToActivity(activity: Class<T>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    private inline fun <reified T> naviToActivity() {
        naviToActivity(T::class.java)
    }
}