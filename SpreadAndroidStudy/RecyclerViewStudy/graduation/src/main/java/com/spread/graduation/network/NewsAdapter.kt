package com.spread.graduation.network

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.cs.News
import com.spread.common.preload.async.inflate.demo.PreloadHelper
import com.spread.graduation.R


class NewsAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<NewsViewHolder>() {

    companion object {
        val layoutId = R.layout.viewholder_news
        const val ASYNC = true
    }

    private val list: MutableList<News> = arrayListOf()

    private val mPreloadHelper = PreloadHelper()

    fun startPreload() {
        if (ASYNC) {
            mPreloadHelper.preload(recyclerView, layoutId, 10)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = if (ASYNC) {
            mPreloadHelper.getView(parent, layoutId)
        } else {
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        }
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = list[position]
        holder.bindData(news.title, news.time, news.introduction)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addNews(news: News) {
        list.add(news)
        notifyItemInserted(list.size - 1)
    }

    fun addNews(news: List<News>) {
        val start = list.size
        list.addAll(news)
        notifyItemRangeInserted(start, news.size)
    }

    fun setNews(news: List<News>) {
        val count = list.size
        list.clear()
        notifyItemRangeRemoved(0, count)
    }
}