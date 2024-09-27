package com.spread.common.preload.prebind

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

open class PreloadLinearLayoutManager(context: Context) : DispatcherLinearLayoutManager(context) {

    constructor(context: Context, preloadMode: PreloadMode) : this(context) {
        this.preloadMode = preloadMode
    }

    // 预渲染模式
    sealed interface PreloadMode
    data object NoPreload : PreloadMode                                 // 没有预渲染
    data object AlwaysPreload : PreloadMode                             // 永远预渲染
    data object PreloadOnIdle : PreloadMode                             // 滚动停止时触发预渲染
    interface PreloadByOutside : PreloadMode {                          // 外界触发预渲染
        data object WithoutTimeout : PreloadByOutside                   // 没有超时补发机制
        data class WithTimeout(val value: Long) : PreloadByOutside      // 有超时补发机制
    }

    private enum class ScrollDirection {
        NONE, FORWARD, BACKWARD
    }

    // 滑动状态监控
    private var mScrollState = RecyclerView.SCROLL_STATE_IDLE
    private val mIsInScroll get() = mScrollState != RecyclerView.SCROLL_STATE_IDLE
    private var scrollDirection: ScrollDirection = ScrollDirection.NONE

    // PreloadByOutside需要的属性
    private var instantExtra = false                   // 一瞬间为true，此时允许给额外空间
    private var timeoutValue = 0L                      // withTimeOut为true时的超时时长
    private var unluckyTryFromOutside = false          // 为true表示很不幸，外界触发预渲染时滚动还没停止，因此停止之后补发。

    private var preloadMode: PreloadMode = NoPreload
        set(value) {
            field = value
            if (value is PreloadByOutside.WithTimeout) {
                this.timeoutValue = value.value
            }
        }

    private var mIsInLayoutChildren = false

    companion object {
        private const val NO_SPACE = true
        private const val EXTRA_SPACE = false
    }

    private val mTriggerPreloadRunnable = Runnable {
        if (!mIsInScroll) { // 手不在屏幕上时才触发
            requestLayout()
        }
    }

    private val mPreloadOnceRunnable = Runnable {
        preloadOnce()
    }

    init {
        // 暂时关掉预取，影响太大了。
        isItemPrefetchEnabled = false
    }

    private val mHandler = Handler(Looper.getMainLooper())

    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        if (needSpaceNow == NO_SPACE) {
            return 0
        }
        val size = if (orientation == RecyclerView.HORIZONTAL) {
            width
        } else {
            height
        }
        return if (size < 0) 0 else size
    }

    /**
     * 此时此刻是否需要额外布局空间？
     * [EXTRA_SPACE] for 需要 and [NO_SPACE] for 不需要
     */
    private val needSpaceNow: Boolean
        get() = when (preloadMode) {
            is AlwaysPreload -> EXTRA_SPACE
            is PreloadOnIdle -> if (isInIdleRequestLayout || !mIsInScroll) EXTRA_SPACE else NO_SPACE
            // FIXME: 当滑动方向突然反向时，isInIdleRequestLayout会导致View瞬间被加载然后回收。
            is PreloadByOutside ->
                if (instantExtra || isInIdleRequestLayout) EXTRA_SPACE else NO_SPACE

            else -> NO_SPACE
        }

    /**
     * 现在考虑这样的情况：刚刚开始滑动，RV从IDLE->DRAGGING。如果子view
     * 处理了TouchEvent，那么从设置DRAGGING和处理第一次scroll就是异步的
     * 过程。因此在这二者之间可以被外部的requestLayout插入。然而，此时的
     * requestLayout导致的额外空间查询有如下特点：
     *     1. mIsInLayoutChildren为ture，因为是外部请求的布局而非滑动事件；
     *     2. 因为还未开始滑动，所以第一个View是紧贴RV的边缘的。
     * 这里的第2点，其实是反推出来的。如果是正常滑动，DRAGGING和处理scroll
     * 为同步过程，而在获取额外空间时，view的layout属性已经发生改动，所以
     * 不是0。
     * 我们不希望在这种情况下返回NO_SPACE，因为这会导致已经被预渲染出来的
     * 卡片被回收。所以对于这种卡片我们需要开一个后门，给它额外的空间。
     * 如果是从下往上滑动，也是同理。只不过是最后一个View要紧贴屏幕。
     *
     * 还有一种情况是，RV自己触发的滚动也会让已经预渲染的卡片被回收。和用户
     * 主动滑动的区别是，后者是从IDLE->DRAGGING而前者是从IDLE->SETTLING。
     */
    private val isInIdleRequestLayout: Boolean
        get() = mIsInScroll && mIsInLayoutChildren && itemFixed

    private val itemFixed: Boolean
        get() = if (childCount > 0) {
            if (scrollDirection == ScrollDirection.BACKWARD) isLastFixed()
            else isFirstFixed()
        } else {
            false
        }

    /**
     * 第一个View是否紧贴在屏幕上，如果为true，表明这次布局有猫腻，来自
     * 外界的requestLayout。因为如果是滑动导致的布局，View是不会紧靠
     * 边缘的。
     */
    private fun isFirstFixed(): Boolean {
        val first = getChildAt(0) ?: return false
        return if (orientation == VERTICAL) first.top == 0 else first.left == 0
    }

    private fun isLastFixed(): Boolean {
        val last = getChildAt(childCount - 1) ?: return false
        return if (orientation == VERTICAL) last.bottom == height else last.right == width
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (mScrollState == RecyclerView.SCROLL_STATE_IDLE && state == RecyclerView.SCROLL_STATE_DRAGGING) {
            instantExtra = false
        }
        mScrollState = state
        Log.d("SpreadPLLM", "current state: ${getStateStr(state)}")
        if (preloadMode is PreloadByOutside && unluckyTryFromOutside && !mIsInScroll) {
            // 针对unluckyTry补发
            unluckyTryFromOutside = false
            removeExistedCallbacks()
            preloadOnce()
        }
        if (preloadMode is PreloadOnIdle) {
            removeExistedCallbacks()
            if (!mIsInScroll) {
                mHandler.post(mTriggerPreloadRunnable)
            }
        } else if (preloadMode is PreloadByOutside.WithTimeout) {
            removeExistedCallbacks()
            mHandler.postDelayed(mPreloadOnceRunnable, timeoutValue)
        }
    }

    private fun getStateStr(state: Int) = when (state) {
        RecyclerView.SCROLL_STATE_DRAGGING -> "DRAGGING"
        RecyclerView.SCROLL_STATE_SETTLING -> "SETTLING"
        else -> "IDLE"
    }

    /**
     * 外界触发预渲染，需要调用这个方法手动触发。
     */
    fun preloadOnce() {
        if (preloadMode !is PreloadByOutside || instantExtra) {
            return
        }
        removeExistedCallbacks()
        if (!mIsInScroll) {
            instantExtra = true
            requestLayout()
        } else {
            unluckyTryFromOutside = true
        }
    }

    private fun removeExistedCallbacks() {
        mHandler.removeCallbacks(mTriggerPreloadRunnable)
        mHandler.removeCallbacks(mPreloadOnceRunnable)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        mIsInLayoutChildren = true
        super.onLayoutChildren(recycler, state)
        mIsInLayoutChildren = false
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        ensureDirection(dy)
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    private fun ensureDirection(dy: Int) {
        if (dy > 0) {
            scrollDirection = ScrollDirection.FORWARD
        } else if (dy < 0) {
            scrollDirection = ScrollDirection.BACKWARD
        }
    }
}