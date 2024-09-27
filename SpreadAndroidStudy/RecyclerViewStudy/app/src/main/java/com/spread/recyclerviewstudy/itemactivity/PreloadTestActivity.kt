package com.spread.recyclerviewstudy.itemactivity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.spread.common.preload.prebind.DefaultVisibilityListener
import com.spread.common.preload.prebind.DispatcherRecyclerView
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.common.preload.prebind.ViewHolderVisibilityDispatcher
import com.spread.common.preload.test.Data
import com.spread.common.preload.test.ItemType
import com.spread.common.preload.test.TestAdapter
import com.spread.recyclerviewstudy.R

class PreloadTestActivity : AppCompatActivity() {

    private lateinit var recyclerview: DispatcherRecyclerView
    private lateinit var layoutManager: PreloadLinearLayoutManager
    private lateinit var adapter: TestAdapter
    private lateinit var preloadBtn: Button
    private lateinit var insertBtn: Button
    private lateinit var preloadModeTextView: TextView
    private val preloadModeChoices = arrayOf(
        "NoPreload", "AlwaysPreload", "PreloadOnIdle", "OutWithoutTimeout", "OutWithTimeout"
    )
    private var choice = 0
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preload_test)
        dialog = AlertDialog.Builder(this)
            .setTitle("Preload Mode:")
            .setSingleChoiceItems(preloadModeChoices, -1) { _, i ->
                choice = i
            }.setNegativeButton("Cancel") { _, _ ->
                finish()
            }.setPositiveButton("Submit") { _, _ ->
                prepare()
            }.setCancelable(false).create()
    }

    override fun onResume() {
        super.onResume()
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    private fun prepare() {
        this.recyclerview = findViewById(R.id.preload_test_recyclerview)
        this.recyclerview.setHasFixedSize(true)
        this.recyclerview.setItemViewCacheSize(0)
        prepareLayoutManager()
        prepareDispatcher()
        prepareAdapter()
        PagerSnapHelper().attachToRecyclerView(this.recyclerview)
        preloadBtn = findViewById(R.id.preload_manually)
        insertBtn = findViewById(R.id.insert_item)
        preloadBtn.setOnClickListener {
            Log.i("Spread-Preload", "preload once!!!")
            this.layoutManager.preloadOnce()
        }
        insertBtn.setOnClickListener {
            val first = layoutManager.findFirstVisibleItemPosition()
            val last = layoutManager.findLastVisibleItemPosition()
            if (first == last) {
                adapter.insertItem(first, Data("Spread", ItemType.DATA_TYPE_OTHER))
            }
        }
        preloadModeTextView = findViewById(R.id.preload_mode_text)
        preloadModeTextView.text = preloadModeChoices[choice]
    }

    private fun prepareAdapter() {
        this.adapter = TestAdapter()
        this.recyclerview.adapter = this.adapter
    }

    private fun prepareDispatcher() {
        ViewHolderVisibilityDispatcher.newComposition(
            recyclerview,
            layoutManager,
            DefaultVisibilityListener()
        )
    }

    private fun prepareLayoutManager() {
        this.layoutManager = PreloadLinearLayoutManager(this, preloadMode)
        recyclerview.layoutManager = this.layoutManager
    }

    private val preloadMode: PreloadLinearLayoutManager.PreloadMode
        get() {
            return when (choice) {
                1 -> PreloadLinearLayoutManager.AlwaysPreload
                2 -> PreloadLinearLayoutManager.PreloadOnIdle
                3 -> PreloadLinearLayoutManager.PreloadByOutside.WithoutTimeout
                4 -> PreloadLinearLayoutManager.PreloadByOutside.WithTimeout(5000L)
                else -> PreloadLinearLayoutManager.NoPreload
            }
        }
}