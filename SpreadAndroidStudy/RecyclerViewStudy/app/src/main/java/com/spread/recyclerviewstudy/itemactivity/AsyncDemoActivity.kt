package com.spread.recyclerviewstudy.itemactivity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.spread.common.preload.async.inflate.demo.PreloadAdapter
import com.spread.common.preload.prebind.DefaultVisibilityListener
import com.spread.common.preload.prebind.PreloadLinearLayoutManager
import com.spread.common.preload.prebind.ViewHolderVisibilityDispatcher
import com.spread.common.preload.test.TestRecyclerView
import com.spread.recyclerviewstudy.R

/**
 * 异步加载View的痛点
 * - 需要合适的时机去启动异步加载，当RV需要数据的时候直接绑定上
 * - 需要提前知道加载的View的类型，不然白加载
 */
class AsyncDemoActivity : AppCompatActivity() {

    private lateinit var recyclerView: TestRecyclerView
    private lateinit var adapter: PreloadAdapter
    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_demo)
        recyclerView = findViewById(R.id.async_demo_rv)
        recyclerView.layoutManager = PreloadLinearLayoutManager(this).apply {
            isItemPrefetchEnabled = false
        }
        adapter = PreloadAdapter(recyclerView)
        adapter.startPreload()
        ViewHolderVisibilityDispatcher.Builder()
            .bindRecyclerView(recyclerView)
            .setListener(DefaultVisibilityListener())
            .build()
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // 所以，AsyncInflate的使用有前提，就是RecyclerView本身在创建的时候最好已经有数据。
        /**
         * 万恶的XUI，它设置完Adapter之后，其实是没有数据的。它故意postDelay了1秒钟才让数据
         * 到达。这就意味着这1秒时间完全可以让异步加载完成。
         */
//        mHandler.postDelayed({
//            recyclerView.adapter = adapter
//        }, 100)
    }
}