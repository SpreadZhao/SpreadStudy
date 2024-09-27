package com.spread.recyclerviewstudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater.Factory2
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.spread.recyclerviewstudy.itemactivity.AdapterBugFixActivity
import com.spread.recyclerviewstudy.itemactivity.AsyncDemoActivity
import com.spread.recyclerviewstudy.itemactivity.AsyncListUtilActivity
import com.spread.recyclerviewstudy.itemactivity.BigItemRecyclerViewActivity
import com.spread.recyclerviewstudy.itemactivity.DispatcherActivity
import com.spread.recyclerviewstudy.itemactivity.PagerSnapActivity
import com.spread.recyclerviewstudy.itemactivity.PreloadTestActivity
import com.spread.recyclerviewstudy.itemactivity.SimpleRecyclerViewActivity
import com.spread.recyclerviewstudy.itemactivity.SmoothScrollActivity
import com.spread.recyclerviewstudy.itemactivity.ViewPager2Activity
import com.spread.recyclerviewstudy.itemactivity.ViewPagerActivity
import com.spread.recyclerviewstudy.itemactivity.VisibilityCheckActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        setFactory()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startTime = System.currentTimeMillis()
        setContentView(R.layout.activity_main)
//        Log.d("SpreadZhaoTest", "setContentView cost: ${System.currentTimeMillis() - startTime}")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mainRecycler = findViewById<RecyclerView>(R.id.main_btns_recycler_view)
        mainRecycler.adapter = MainBtnsAdapter()
        mainRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun <T> naviToActiviy(activity: Class<T>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    private fun setFactory() {
        LayoutInflaterCompat.setFactory2(layoutInflater, object : Factory2 {
            override fun onCreateView(
                parent: View?,
                name: String,
                context: Context,
                attrs: AttributeSet
            ): View? {
                return delegate.createView(parent, name, context, attrs)
            }

            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                TODO("Not yet implemented")
            }
        })
    }

    inner class MainBtnsAdapter : RecyclerView.Adapter<MainBtnsViewHolder>() {

        private val btnInfos = listOf(
            directButton<SimpleRecyclerViewActivity>("Simple Recycler View"),
            directButton<ViewPagerActivity>("View Pager"),
            directButton<ViewPager2Activity>("View Pager 2"),
            directButton<PagerSnapActivity>("Pager Snap"),
            directButton<BigItemRecyclerViewActivity>("Big Item"),
            directButton<DispatcherActivity>("Dispatcher"),
            directButton<SmoothScrollActivity>("Smooth Scroll"),
            directButton<AsyncListUtilActivity>("Async List Util"),
            directButton<AsyncDemoActivity>("Async Demo"),
            directButton<AdapterBugFixActivity>("Adapter Bug Fix"),
            directButton<PreloadTestActivity>("Preload Test"),
            directButton<VisibilityCheckActivity>("Check Visibility")
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBtnsViewHolder {
            val btnView = Button(parent.context).apply {
                layoutParams =
                    ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                gravity = Gravity.CENTER
            }
            return MainBtnsViewHolder(btnView)
        }

        override fun onBindViewHolder(holder: MainBtnsViewHolder, position: Int) {
            holder.btn.apply {
                val info = btnInfos[position]
                text = info.displayName
                setOnClickListener { naviToActiviy(info.target) }
            }
        }

        override fun getItemCount() = btnInfos.size
    }

    inner class MainBtnsViewHolder(val btn: Button) : ViewHolder(btn)

    class ButtonInfo(val displayName: String, val target: Class<*>)
}

private inline fun <reified T> directButton(displayName: String): MainActivity.ButtonInfo {
    return MainActivity.ButtonInfo(displayName, T::class.java)
}