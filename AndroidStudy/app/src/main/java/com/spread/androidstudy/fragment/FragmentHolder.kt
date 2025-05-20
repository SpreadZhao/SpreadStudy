package com.spread.androidstudy.fragment

import androidx.fragment.app.Fragment
import com.spread.androidstudy.R

class FragmentHolder {

    private val fragments = HashMap<Int, Fragment>()

    fun getFragment(id: Int): Fragment? {
        if (!fragments.containsKey(id)) {
            val fragment = newFragment(id)
            if (fragment != null) {
                fragments[id] = fragment
                return fragment
            }
        }
        return fragments[id]
    }

    private fun newFragment(id: Int) = when (id) {
        R.id.menu_item_livedata -> LiveDataFragment()
        R.id.menu_item_twice_xy_bug -> TwiceXYBugFragment()
        else -> null
    }

}