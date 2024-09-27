package com.spread.common.preload.prebind

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.spread.common.R

class ViewHolderVisibilityDispatcher private constructor() : IViewHolderVisibilityDispatcher {

    private lateinit var mRecyclerView: RecyclerView

    private val TAG = "Dispatcher-Spread"

    companion object {

        /**
         * New relation of RecyclerView, LayoutManager and VisibilityDispatcher.
         */
        fun newComposition(
            recyclerView: DispatcherRecyclerView,
            layoutManager: DispatcherLinearLayoutManager,
            listener: VisibilityListener
        ) {
            val dispatcher = ViewHolderVisibilityDispatcher()
            dispatcher.bindRecyclerView(recyclerView)
            dispatcher.bindLayoutManager(layoutManager)
            dispatcher.setListener(listener)
        }

        fun newComposition(
            recyclerView: DispatcherRecyclerView,
            layoutManager: DispatcherLinearLayoutManager,
            listenerInit: DSLVisibilityListener.() -> Unit
        ) {
            val dispatcher = ViewHolderVisibilityDispatcher()
            dispatcher.bindRecyclerView(recyclerView)
            dispatcher.bindLayoutManager(layoutManager)
            dispatcher.setListener(DSLVisibilityListener().apply(listenerInit))
        }
    }

    class DSLVisibilityListener : VisibilityListener {
        private var onViewHolderVisible: ((ViewHolder, Reason) -> Unit)? = null
        private var onViewHolderInvisible: ((ViewHolder, Reason) -> Unit)? = null
        override fun onViewHolderVisible(holder: ViewHolder, reason: Reason) {
            this.onViewHolderVisible?.invoke(holder, reason)
        }

        override fun onViewHolderInvisible(holder: ViewHolder, reason: Reason) {
            this.onViewHolderInvisible?.invoke(holder, reason)
        }

        fun onViewHolderVisible(onVisible: (ViewHolder, Reason) -> Unit) {
            this.onViewHolderVisible = onVisible
        }

        fun onViewHolderInVisible(onInvisible: (ViewHolder, Reason) -> Unit) {
            this.onViewHolderInvisible = onInvisible
        }
    }

    @Deprecated("You won't need dispatcher.", ReplaceWith("ViewHolderVisibilityDispatcher.init()"))
    class Builder {

        private val dispatcher = ViewHolderVisibilityDispatcher()

        fun build(): ViewHolderVisibilityDispatcher {
            return dispatcher
        }

        fun bindRecyclerView(recyclerView: DispatcherRecyclerView) = this.apply {
            dispatcher.bindRecyclerView(recyclerView)
        }

        fun bindLayoutManager(layoutManager: DispatcherLinearLayoutManager) = this.apply {
            dispatcher.bindLayoutManager(layoutManager)
        }

        fun setListener(listener: VisibilityListener) = this.apply {
            dispatcher.setListener(listener)
        }

        fun setListener(init: DSLVisibilityListener.() -> Unit) = this.apply {
            val listener = DSLVisibilityListener()
            listener.init()
            dispatcher.setListener(listener)
        }

    }

    interface VisibilityListener {
        fun onViewHolderVisible(holder: ViewHolder, reason: Reason)
        fun onViewHolderInvisible(holder: ViewHolder, reason: Reason)
    }

    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null
    private var mListener: VisibilityListener? = null

    private fun setListener(listener: VisibilityListener) {
        if (mListener == null) {
            mListener = listener
        }
    }

    private fun isChildVisibleVertical(itemView: View): Boolean {
        val verticalHelper = getVerticalHelper(mRecyclerView.layoutManager) ?: return false
        val itemStart = verticalHelper.getDecoratedStart(itemView)
        val itemEnd = verticalHelper.getDecoratedEnd(itemView)
        return !(itemEnd <= 0 || itemStart >= mRecyclerView.height)
    }

    private fun handleAttachedVHsVisibility(reason: Reason) {
        for (i in 0 until mRecyclerView.childCount) {
            val child = mRecyclerView.getChildAt(i)
            val oldVisible = child.isVisibleByTag
            val newVisible = isChildVisibleVertical(child)
            if (oldVisible == newVisible) continue
            child.isVisibleByTag = newVisible
            mListener?.let {
                val holder = child.viewHolder ?: return@let
                if (newVisible) {
                    it.onViewHolderVisible(holder, reason)
                } else {
                    it.onViewHolderInvisible(holder, reason)
                }
            }
        }
    }

    private fun handleDetachedChildVisibility(itemView: View) {
        val oldVisible = itemView.isVisibleByTag
        if (oldVisible && mListener != null) {
            itemView.isVisibleByTag = false
            val holder = itemView.viewHolder ?: return
//        Log.d(TAG, "Detach[${holder.adapterPosition + 1}] oldVisible: $oldVisible")
            mListener!!.onViewHolderInvisible(holder, Reason.DETACH)
        }
    }

    private val View.viewHolder: ViewHolder?
        get() = mRecyclerView.getChildViewHolder(this)

    private var View.isVisibleByTag: Boolean
        get() = getTag(R.id.tag_view_holder_visibility) == true
        set(value) {
            setTag(R.id.tag_view_holder_visibility, value)
        }

    private fun getVerticalHelper(layoutManger: RecyclerView.LayoutManager?): OrientationHelper? {
        if (mVerticalHelper == null || mVerticalHelper?.layoutManager !== layoutManger) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManger)
        }
        return mVerticalHelper
    }

    override fun dispatchVisibility(occasion: Occasion) {
        when (occasion) {
            is Occasion.Scroll -> handleAttachedVHsVisibility(Reason.SCROLL)
            is Occasion.Detach -> handleDetachedChildVisibility(occasion.itemView)
            is Occasion.LayoutComplete -> handleAttachedVHsVisibility(Reason.LAYOUT_COMPLETE)
        }
    }

    override fun bindRecyclerView(recyclerView: DispatcherRecyclerView) {
        recyclerView.dispatcher = this
        mRecyclerView = recyclerView
    }

    override fun bindLayoutManager(layoutManager: DispatcherLinearLayoutManager) {
        layoutManager.dispatcher = this
    }
}