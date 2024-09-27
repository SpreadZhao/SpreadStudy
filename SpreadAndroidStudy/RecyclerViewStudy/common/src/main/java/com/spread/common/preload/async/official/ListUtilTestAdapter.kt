package com.spread.common.preload.async.official

import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.preload.test.Data
import com.spread.common.preload.test.MyViewHolder
import com.spread.common.preload.test.TestAdapter

class ListUtilTestAdapter(
    recyclerView: RecyclerView,
    private var enableAsyncLoading: Boolean,
    loadCount: Int
) : TestAdapter() {

//    object Builder {
//        lateinit var recyclerView: RecyclerView
//        lateinit var enableAsyncLoading: Boolean
//            set(value) {
//
//            }
//        fun build(): AsyncTestAdapter {
//            return AsyncTestAdapter(recyclerView)
//        }
//    }

    // TODO: 记一下为什么这么写初始化逻辑
    private val listUtil: AsyncListUtil<Data>?

    init {
        if (enableAsyncLoading) {
            listUtil = AsyncListUtil(
                Data::class.java,
                loadCount,
                DataCallBack(dataSet),
                ViewCallback(recyclerView)
            )
            recyclerView.addOnScrollListener(ScrollListener(listUtil))
        } else {
            listUtil = null
        }
    }

    override fun getItemCount(): Int {
        return if (enableAsyncLoading) {
            listUtil?.itemCount ?: 0
        } else {
            dataSet.size
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (enableAsyncLoading) {
            listUtil?.getItem(position)?.let { holder.bindData(it) }
        } else {
            holder.bindData(dataSet[position])
        }
    }

    override fun onViewRecycled(holder: MyViewHolder) {
        holder.print("recycle")
        super.onViewRecycled(holder)
    }

    class ScrollListener(private val util: AsyncListUtil<Data>) : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            util.onRangeChanged()
        }
    }

}