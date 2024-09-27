package com.spread.ostepstudy.common.terminal

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spread.ostepstudy.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TerminalAdapter(context: Context, private val mRecyclerView: RecyclerView) : RecyclerView.Adapter<TerminalViewHolder>() {

    data class Msg(val msg: String, val time: String)

    companion object {
        private const val TERMINAL_MSG_MAX_SIZE = 99
        private val dateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA)
    }

    private val inflater = LayoutInflater.from(context)

    private val msgList: ArrayDeque<Msg> = ArrayDeque()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerminalViewHolder {
        val rootView = inflater.inflate(R.layout.viewholder_terminal, parent, false)
        return TerminalViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: TerminalViewHolder, position: Int) {
        if (position !in msgList.indices) {
            return
        }
        val msg = msgList[position]
        holder.bind(position + 1, msg)
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    fun addMsg(msg: String) {
        if (msgList.size > TERMINAL_MSG_MAX_SIZE) {
            val size = msgList.size
            msgList.clear()
            notifyItemRangeRemoved(0, size)
        }
        val time = dateFormat.format(Date())
        msgList.addLast(Msg(msg, time))
        notifyItemInserted(msgList.lastIndex)
//        val lm = (mRecyclerView.layoutManager as? LinearLayoutManager) ?: return
//        val lastVisibleItem = lm.getChildAt(lm.findLastVisibleItemPosition()) ?: return
//        val d = lm.height - lm.getDecoratedBottom(lastVisibleItem)
//        Log.d("Spread-OSTEP", "d: $d")
//        if (d < 10) {
//            mRecyclerView.scrollToPosition(msgList.size - 1)
//        }
    }
}