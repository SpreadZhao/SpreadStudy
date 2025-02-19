package com.spread.androidstudy.fragment

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener

interface DefaultDrawerListener : DrawerListener {
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerOpened(drawerView: View) {

    }
    override fun onDrawerClosed(drawerView: View) {

    }

    override fun onDrawerStateChanged(newState: Int) {

    }

}