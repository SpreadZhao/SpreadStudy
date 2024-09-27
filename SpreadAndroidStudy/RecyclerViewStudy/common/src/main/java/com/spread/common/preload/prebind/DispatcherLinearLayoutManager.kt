package com.spread.common.preload.prebind

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class DispatcherLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    internal var dispatcher: IViewHolderVisibilityDispatcher? = null

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        dispatcher?.dispatchVisibility(Occasion.LayoutComplete)
    }
}