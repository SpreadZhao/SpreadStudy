package com.spread.common.preload.async.official

import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewCallback(private val recyclerView: RecyclerView) : AsyncListUtil.ViewCallback() {
    override fun getItemRangeInto(outRange: IntArray) {
        (recyclerView.layoutManager as? LinearLayoutManager)?.let {
            outRange[0] = it.findFirstVisibleItemPosition()
            outRange[1] = it.findLastVisibleItemPosition()
        }
        if (outRange[0] == -1 && outRange[1] == -1) {
            outRange[0] = 0
            outRange[1] = 0
        }
    }

    override fun onDataRefresh() {
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onItemLoaded(position: Int) {
        recyclerView.adapter?.notifyItemChanged(position)
    }

}