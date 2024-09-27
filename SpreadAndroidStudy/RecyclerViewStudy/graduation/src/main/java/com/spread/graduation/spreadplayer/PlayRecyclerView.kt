package com.spread.graduation.spreadplayer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.spread.common.preload.prebind.DispatcherRecyclerView
import com.spread.graduation.tryfirst.VideoPagerSnapHelper

class PlayRecyclerView : DispatcherRecyclerView {

    private val mSnapHelper = VideoPagerSnapHelper()
    private val mAdapter: PlayAdapter?
        get() = adapter as? PlayAdapter

    private var mCurrPosition = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setHasFixedSize(true)
        setItemViewCacheSize(0)
        mSnapHelper.attachToRecyclerView(this)
        mSnapHelper.addSnapListener(object : VideoPagerSnapHelper.SnapListener {
            override fun onSnapToPosition(position: Int) {
                mAdapter?.onHolderSelect(position)
                mCurrPosition = position
            }

            override fun onFindSnapView(view: View) {
                if (mAdapter == null || layoutManager == null) {
                    return
                }
                val targetPosition = layoutManager!!.getPosition(view)
                if (targetPosition == mCurrPosition) {
                    return
                }
                mAdapter!!.onHolderSelect(targetPosition)
            }
        })
    }


}