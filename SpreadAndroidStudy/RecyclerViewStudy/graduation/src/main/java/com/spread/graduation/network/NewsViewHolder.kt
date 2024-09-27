package com.spread.graduation.network

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spread.graduation.R

class NewsViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val titleView: TextView = rootView.findViewById(R.id.news_title)
    val timeView: TextView = rootView.findViewById(R.id.news_time)
    val introductionView: TextView = rootView.findViewById(R.id.news_introduction)

    fun bindData(title: String, time: String, introduction: String) {
        titleView.text = title
        timeView.text = time
        introductionView.text = introduction
    }
}