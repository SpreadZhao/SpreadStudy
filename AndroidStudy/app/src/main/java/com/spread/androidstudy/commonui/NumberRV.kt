package com.spread.androidstudy.commonui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spread.androidstudy.R

class NumberRV : RecyclerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val lm = LinearLayoutManager(context)
        lm.isItemPrefetchEnabled = false
        lm.initialPrefetchItemCount = 0
        setItemViewCacheSize(0)
        this.layoutManager = lm
        this.adapter = Adapter()
    }

    private class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        private val list = (1..100).toList()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = TextView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                200
            )
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position % 2 == 0) {
                holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.c1, null))
            } else {
                holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.c2, null))
            }
            val number = list[position].toString()
            (holder.itemView as TextView).text = number
            Log.d("NumberRV", "bind $number")
        }

    }



}