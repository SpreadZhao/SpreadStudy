package com.spread.common.preload.prebind

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.spread.common.preload.test.MyViewHolder

class DefaultVisibilityListener(private val tag: String = "SpreadZhaoTest") :
    ViewHolderVisibilityDispatcher.VisibilityListener {
    override fun onViewHolderVisible(holder: ViewHolder, reason: Reason) {
        if (holder is MyViewHolder) {
            holder.print("visible on ${getReasonStr(reason)}", tag)
        }
    }

    override fun onViewHolderInvisible(holder: ViewHolder, reason: Reason) {
        if (holder is MyViewHolder) {
            holder.print("invisible on ${getReasonStr(reason)}", tag)
        }
    }

    private fun getReasonStr(reason: Reason) = when (reason) {
        Reason.SCROLL -> "Scroll"
        Reason.DETACH -> "Detach"
        Reason.LAYOUT_COMPLETE -> "Layout Complete"
    }
}