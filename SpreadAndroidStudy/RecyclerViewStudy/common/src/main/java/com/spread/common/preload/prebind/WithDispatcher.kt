package com.spread.common.preload.prebind

/**
 * 一个有Dispatcher的RecyclerView, LayoutManger, Adapter等
 */
@Deprecated("过度设计")
interface WithDispatcher {
    fun setDispatcher(dispatcher: IViewHolderVisibilityDispatcher)
    fun getDispatcher(): IViewHolderVisibilityDispatcher?
}