package com.spread.common.preload.async.inflate.demo

import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import java.lang.ref.SoftReference
import java.util.LinkedList
import kotlin.math.min

class PreloadHelper {

    companion object {
        private const val DEFAULT_PRELOAD_COUNT = 5
        const val TAG = "Spread-Preload"
    }

    private val mViewCache = ViewCache()

    fun getView(parent: ViewGroup, layoutId: Int): View {
        return getView(parent, layoutId, DEFAULT_PRELOAD_COUNT)
    }

    fun getView(parent: ViewGroup, layoutId: Int, maxCount: Int): View {
        val view = mViewCache.getView(layoutId)
        if (view != null) {
            Log.d(TAG, "getView from cache")
            preloadOnce(parent, layoutId, maxCount)
            return view
        }
        Log.d(TAG, "inflate view manually")
        return DefaultAsyncInflater.syncInflate(parent, layoutId)
    }

    fun preloadOnce(parent: ViewGroup, layoutId: Int) {
        preloadOnce(parent, layoutId, DEFAULT_PRELOAD_COUNT)
    }

    fun preloadOnce(parent: ViewGroup, layoutId: Int, maxCount: Int) {
        preload(parent, layoutId, maxCount, 1)
    }

    fun preload(parent: ViewGroup, layoutId: Int) {
        preload(parent, layoutId, DEFAULT_PRELOAD_COUNT, 0)
    }

    fun preload(parent: ViewGroup, layoutId: Int, maxCount: Int) {
        preload(parent, layoutId, maxCount, 0)
    }

    fun preload(parent: ViewGroup, layoutId: Int, maxCount: Int, forceLoadCount: Int) {
        val viewsAvailableCount = mViewCache.getViewPoolAvailableCount(layoutId)
        if (viewsAvailableCount >= maxCount) {
            return
        }
        var needPreloadCount = maxCount - viewsAvailableCount
        if (forceLoadCount > 0) {
            needPreloadCount = min(forceLoadCount, needPreloadCount)
        }
        repeat(needPreloadCount) {
            preAsyncInflateView(parent, layoutId)
        }
    }

    private fun preAsyncInflateView(parent: ViewGroup, layoutId: Int) {
        DefaultAsyncInflater.asyncInflate(parent, layoutId, object : InflateCallback {
            override fun onInflateFinished(layoutId: Int, view: View) {
                mViewCache.putView(layoutId, view)
                Log.d(
                    TAG,
                    "async inflate finish, have put into pool, [${view.hashCode()}], layoutId: $layoutId, view: $view, currPoolSize: ${
                        mViewCache.getViewPool(layoutId).size
                    }"
                )
            }
        })
    }

    class ViewCache {
        private val mViewPools = SparseArray<LinkedList<SoftReference<View>>>()

        fun getViewPool(layoutId: Int): LinkedList<SoftReference<View>> {
            var views = mViewPools[layoutId]
            if (views == null) {
                views = LinkedList()
                mViewPools[layoutId] = views
            }
            return views
        }

        fun getViewPoolAvailableCount(layoutId: Int): Int {
            val views = getViewPool(layoutId)
            val it = views.iterator()
            var count = 0
            while (it.hasNext()) {
                if (it.next().get() != null) {
                    count++
                } else {
                    it.remove()
                }
            }
            return count
        }

        fun putView(layoutId: Int, view: View) {
            if (view == null) {
                return
            }
            getViewPool(layoutId).offer(SoftReference(view))
        }

        fun getView(layoutId: Int): View? {
            return getViewFromPool(getViewPool(layoutId))
        }

        private fun getViewFromPool(views: LinkedList<SoftReference<View>>): View? {
            if (views.isEmpty()) {
                return null
            }
            return views.pop().get() ?: return getViewFromPool(views)
        }
    }

    interface ILayoutInflater {
        fun asyncInflate(parent: ViewGroup, layoutId: Int, callback: InflateCallback)
        fun syncInflate(parent: ViewGroup, layoutId: Int): View
    }

    interface InflateCallback {
        fun onInflateFinished(layoutId: Int, view: View)
    }
}