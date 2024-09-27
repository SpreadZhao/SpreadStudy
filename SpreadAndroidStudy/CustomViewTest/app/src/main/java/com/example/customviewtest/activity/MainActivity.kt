package com.example.customviewtest.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.customviewtest.R


class MainActivity : AppCompatActivity(), OnClickListener {

    private val buttons = ArrayList<Button>()

    private val ids = listOf(
        R.id.simple_btn,
        R.id.trouble_btn,
        R.id.view_pager_btn,
        R.id.view_pager2_btn,
        R.id.pager_snap_btn,
        R.id.hook_do_frame_btn,
        R.id.gesture_layout_btn
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findButtonsAndAdd<Button>(ids)
        setListener()
    }

    private inline fun <reified V : Button> findButtonsAndAdd(ids: List<Int>) {
        ids.forEach {
            val view = findViewById<V>(it)
            buttons.add(view)
        }
    }

    private fun setListener() {
        buttons.forEach { it.setOnClickListener(this) }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.simple_btn -> naviToActivity<SimpleActivity>()
            R.id.trouble_btn -> naviToActivity<TroubleActivity>()
            R.id.view_pager_btn -> naviToActivity<ViewPagerActivity>()
            R.id.view_pager2_btn -> naviToActivity<ViewPager2Activity>()
            R.id.pager_snap_btn -> naviToActivity<PagerSnapActivity>()
            R.id.hook_do_frame_btn -> naviToActivity<DoFrameHookActivity>()
            R.id.gesture_layout_btn -> naviToActivity<GestureLayoutActivity>()
        }
    }
    private inline fun <reified ACTIVITY : Activity> naviToActivity() {
        val intent = Intent(this, ACTIVITY::class.java)
        startActivity(intent)
    }


}