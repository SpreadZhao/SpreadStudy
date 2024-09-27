package com.spread.common.preload.prebind

interface IViewHolderVisibilityDispatcher {
    fun dispatchVisibility(occasion: Occasion)
    fun bindRecyclerView(recyclerView: DispatcherRecyclerView)
    fun bindLayoutManager(layoutManager: DispatcherLinearLayoutManager)
}
