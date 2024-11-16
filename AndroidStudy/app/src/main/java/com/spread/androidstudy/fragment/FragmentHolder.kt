package com.spread.androidstudy.fragment

import androidx.fragment.app.Fragment

class FragmentHolder {

    private val mFragments = HashMap<Int, Fragment>()

    fun getFragment(id: Int): Fragment? {
        if (!mFragments.containsKey(id)) {
            val fragment = newFragment(id)
            if (fragment != null) {
                mFragments[id] = fragment
                return fragment
            }
        }
        return mFragments[id]
    }

    private fun newFragment(id: Int) = when (id) {
        else -> null
    }

}