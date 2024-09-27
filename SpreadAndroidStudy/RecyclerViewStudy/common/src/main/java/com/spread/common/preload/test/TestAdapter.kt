package com.spread.common.preload.test

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.R

open class TestAdapter : RecyclerView.Adapter<MyViewHolder>() {

    companion object {
        private const val TAG = "TestAdapter"
    }

    protected val dataSet = createListData(1..100, ItemType.DATA_TYPE_EVEN, ItemType.DATA_TYPE_ODD)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = if (viewType == ItemType.DATA_TYPE_EVEN) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.big_text_with_moving_text, parent, false).apply {
                background = ColorDrawable(Color.parseColor("#CC0033"))
            }
        } else if (viewType == ItemType.DATA_TYPE_ODD) {
            LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false).apply {
                background = ColorDrawable(Color.parseColor("#0066CC"))
            }
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false).apply {
                background = ColorDrawable(Color.parseColor("#f47920"))
            }
        }
        return MyViewHolder(view)
    }

    fun insertItem(index: Int, newData: Data) {
        dataSet.add(index, newData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(dataSet[position])
        Log.d(TAG, "bind: ${holder.textView.text}")
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.d(TAG, "detach: ${holder.textView.text}")
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.d(TAG, "attach: ${holder.textView.text}")
    }

    override fun onViewRecycled(holder: MyViewHolder) {
        super.onViewRecycled(holder)
        Log.d(TAG, "recycle: ${holder.textView.text}")
    }

    override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int) = dataSet[position].type
}

fun createListData(fromTo: IntRange, type1: Int, type2: Int): MutableList<Data> {
    val res = mutableListOf<Data>()
    for (i in fromTo) {
        val type = if (i % 2 == 0) type1 else type2
        res.add(Data(i.toString(), type))
    }
    return res
}

fun TextView.adjustGravity() = this.apply {
    gravity = Gravity.CENTER

    layoutParams =
        FrameLayout.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        ).apply {
            this.gravity = Gravity.CENTER
        }
}

fun TextView.adjustGravityForType() = this.apply {
    gravity = Gravity.CENTER

    layoutParams =
        FrameLayout.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        ).apply {
            this.gravity = Gravity.CENTER or Gravity.BOTTOM
        }
}

fun getColorByType(viewType: Int) = when (viewType) {
    ItemType.DATA_TYPE_EVEN -> ColorDrawable(Color.parseColor("#CC0033"))
    ItemType.DATA_TYPE_ODD -> ColorDrawable(Color.parseColor("#0066CC"))
    else -> ColorDrawable(Color.parseColor("#f47920"))
}

data class Data(val str: String, val type: Int)