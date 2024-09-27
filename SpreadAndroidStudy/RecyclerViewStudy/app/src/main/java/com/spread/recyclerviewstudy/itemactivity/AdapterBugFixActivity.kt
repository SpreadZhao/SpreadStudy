package com.spread.recyclerviewstudy.itemactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.common.preload.test.TestAdapter
import com.spread.recyclerviewstudy.R

class AdapterBugFixActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter_bug_fix)
        recyclerView = findViewById(R.id.bug_fix_rv)
        recyclerView.layoutManager = PreloadLinearLayoutManager(this).apply {
        }
        recyclerView.adapter = TestAdapter()
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }
}