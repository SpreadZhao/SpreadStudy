package com.spread.graduation.network

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.cs.FakeNetwork
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.common.tool.UITools
import com.spread.common.visible.VisibleChecker
import com.spread.graduation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val newsContainer = findViewById<FrameLayout>(R.id.news_container)
        recyclerView = findViewById(R.id.news_rv)
        val visibleChecker = VisibleChecker(object : VisibleChecker.ICheckEE {
            override val rootView: View
                get() = newsContainer
            override val totalWidth: Int
                get() = UITools.getRealScreenWidth(this@NewsActivity)
            override val totalHeight: Int
                get() = UITools.getRealScreenHeight(this@NewsActivity)
            override val expectedTime: Long
                get() = 12000L
            override val validRatio: Float
                get() = 0.3F

        })
        visibleChecker.start()
        adapter = NewsAdapter(recyclerView)
        adapter.startPreload()
        recyclerView.layoutManager = PreloadLinearLayoutManager(
            this,
            PreloadLinearLayoutManager.NoPreload
        )
        recyclerView.adapter = adapter
        CoroutineScope(Dispatchers.Main).launch {
            val newsList = FakeNetwork.getNewsList()
            adapter.addNews(newsList)
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000 * 10)
//            visibleChecker.stop()
        }
        findViewById<Button>(R.id.news_button_traversal).setOnClickListener {
            visibleChecker.manualTraversal()
        }
    }
}