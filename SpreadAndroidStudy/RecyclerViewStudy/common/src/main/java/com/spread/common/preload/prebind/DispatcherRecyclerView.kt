package com.spread.common.preload.prebind

import android.content.Context
import android.util.AttributeSet
import android.view.View

open class DispatcherRecyclerView : LogRecyclerView {

    internal var dispatcher: IViewHolderVisibilityDispatcher? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        dispatcher?.dispatchVisibility(Occasion.Scroll)
    }

    override fun onChildDetachedFromWindow(child: View) {
        super.onChildDetachedFromWindow(child)
        dispatcher?.dispatchVisibility(Occasion.Detach(child))
    }
}