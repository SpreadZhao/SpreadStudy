package com.spread.common.preload.async.inflate.demo

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.spread.common.R
import com.spread.common.preload.test.MyViewHolder
import com.spread.common.preload.test.TestAdapter
import com.spread.common.preload.test.getColorByType

class PreloadAdapter(private val recyclerview: RecyclerView) : TestAdapter() {

    private val mPreloadHelper = PreloadHelper()

    companion object {
        val layoutId = R.layout.big_text
    }

    fun startPreload() {
        mPreloadHelper.preload(recyclerview, layoutId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = mPreloadHelper.getView(parent, layoutId).apply {
            updateLayoutParams<LayoutParams> {
                width = LayoutParams.MATCH_PARENT
                height = LayoutParams.WRAP_CONTENT
                background = getColorByType(viewType)
            }
        }
        return MyViewHolder(view)
    }

}