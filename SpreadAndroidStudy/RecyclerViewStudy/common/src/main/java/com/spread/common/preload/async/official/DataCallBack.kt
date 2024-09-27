package com.spread.common.preload.async.official

import androidx.recyclerview.widget.AsyncListUtil
import com.spread.common.preload.test.Data

class DataCallBack(private val dataList: List<Data>) : AsyncListUtil.DataCallback<Data>() {
    override fun refreshData(): Int {
        return dataList.size
    }

    override fun fillData(data: Array<Data>, startPosition: Int, itemCount: Int) {
        for (i in 0 until itemCount) {
            data[i] = dataList[startPosition + i]
        }
    }

}