package com.spread.recyclerviewstudy.itemactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.spread.common.preload.async.official.ListUtilTestAdapter
import com.spread.common.preload.prebind.DefaultVisibilityListener
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.common.preload.prebind.ViewHolderVisibilityDispatcher
import com.spread.common.preload.test.TestRecyclerView
import com.spread.recyclerviewstudy.R

class AsyncListUtilActivity : AppCompatActivity() {

    private lateinit var recyclerView: TestRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_list_util)
        recyclerView = findViewById(R.id.async_list_util_rv)
        recyclerView.layoutManager = PreloadLinearLayoutManager(this).apply {
            isItemPrefetchEnabled = false
        }
        ViewHolderVisibilityDispatcher.Builder()
            .bindRecyclerView(recyclerView)
            .setListener(DefaultVisibilityListener())
            .build()
        recyclerView.adapter = ListUtilTestAdapter(recyclerView, true, 2)
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }
}