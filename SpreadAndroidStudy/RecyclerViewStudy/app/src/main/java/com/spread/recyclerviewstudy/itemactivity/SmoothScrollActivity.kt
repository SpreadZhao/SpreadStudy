package com.spread.recyclerviewstudy.itemactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.common.preload.test.TestAdapter
import com.spread.common.tool.BottomToolbarButtonsAdapter
import com.spread.common.tool.ButtonItem
import com.spread.recyclerviewstudy.R

class SmoothScrollActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var bottomRV: RecyclerView
    private var lastPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smooth_scroll)
        rv = findViewById(R.id.smooth_scroll_rv)
        val layoutManager = this.layoutManager
        rv.layoutManager = layoutManager
        val adapter = TestAdapter()
        rv.adapter = adapter
        PagerSnapHelper().attachToRecyclerView(rv)

        bottomRV = findViewById(R.id.bottom_toolbar)
        bottomRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false).apply {
            isItemPrefetchEnabled = false
        }
        bottomRV.adapter = BottomToolbarButtonsAdapter(bottomRV, listOf(
            ButtonItem("Smooth Scroll") {
                rv.smoothScrollToPosition(++lastPosition)
            }
        ))
    }

    private val layoutManager =
        object : PreloadLinearLayoutManager(this@SmoothScrollActivity, PreloadOnIdle) {

            override fun smoothScrollToPosition(
                recyclerView: RecyclerView?,
                state: RecyclerView.State?,
                position: Int
            ) {
                val scrollListener = LinearSmoothScroller(rv.context)
                scrollListener.targetPosition = position
                startSmoothScroll(scrollListener)
            }
        }
}