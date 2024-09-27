package com.spread.ostepstudy.common.terminal

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spread.ostepstudy.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TerminalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    private val numView: TextView = itemView.findViewById(R.id.viewholder_terminal_num)
    private val msgView: TextView = itemView.findViewById(R.id.viewholder_terminal_msg)
    private val timeView: TextView = itemView.findViewById(R.id.viewholder_terminal_time)

    fun bind(num: Int, msg: TerminalAdapter.Msg) {
        numView.text = num.toString()
        msgView.text = msg.msg
        timeView.text = msg.time
    }
}