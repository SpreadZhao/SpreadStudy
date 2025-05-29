package com.spread.androidstudy.fragment

import android.os.Bundle
import android.view.View
import com.spread.androidstudy.R

/**
 * 这教会了我们一个道理，设置Constraint的时候，只需要贴一个就行了。
 *
 * ABC三个View,A最上面，C最下面，B中间。要保证A是wrapcontent,c是wrapcontent
 * 然后B要占满剩下的空间。
 *
 * 这个时候让B的高度是0dp，然后设置上下的constraint。这就够了。
 * 不要手欠给A和C也连上B。不然B会被布局好几次。
 * 在我这个例子里，应该是3次。
 * 看起来没啥问题，但是如果B是一个RecyclerView，就会导致屏幕外
 * 的内容也被bind，然后再回收。就好像C消失了一样。
 */
class BadRVFirstFrameFragment : BaseFragment(R.layout.fragment_bad_rv_frame_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}