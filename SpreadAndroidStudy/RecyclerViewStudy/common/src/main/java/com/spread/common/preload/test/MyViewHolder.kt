package com.spread.common.preload.test

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.big_text_text)
    val typeView: TextView = itemView.findViewById(R.id.big_text_type)
    fun bindData(data: Data) {
        textView.text = data.str
        textView.textSize = 100f
        typeView.text = data.type.toString()
        textView.adjustGravity()
        typeView.adjustGravityForType()
        print("bind")
    }

    fun print(event: String, tag: String = "SpreadZhaoTest") {
        Log.d(tag, "$event[${hashCode()}]: text: ${textView.text}")
    }
}