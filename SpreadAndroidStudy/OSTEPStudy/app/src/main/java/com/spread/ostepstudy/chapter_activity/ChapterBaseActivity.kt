package com.spread.ostepstudy.chapter_activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.spread.ostepstudy.R
import com.spread.ostepstudy.chapter_fragment.BaseFragment

abstract class ChapterBaseActivity : AppCompatActivity() {

    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private val mFragmentManager = supportFragmentManager

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (drawer.isOpen) {
                drawer.closeDrawers()
                isEnabled = false
            }
        }
    }

    private val navListener = OnNavigationItemSelectedListener { item ->
        val id = item.itemId
        if (!navItemMap.contains(id)) {
            return@OnNavigationItemSelectedListener false
        }
        val fragment: BaseFragment = navItemMap[id] ?: return@OnNavigationItemSelectedListener false
        switchToFragment(fragment)
        toolbar.subtitle = fragment.subtitle
        drawer.closeDrawers()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_base)
        init()
    }

    private fun init() {
        this.navView = findViewById(R.id.nav_view)
        this.toolbar = findViewById(R.id.main_toolbar)
        this.drawer = findViewById(R.id.main_drawer)
        setNavItems(navItemsId)
        this.navView.setNavigationItemSelectedListener(navListener)
        // must before setSupportActionBar(toolbar)
        this.toolbar.title = this.title
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground)
        }
//        onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    private fun setNavItems(id: Int) {
        navView.menu.clear()
        navView.inflateMenu(id)
    }

    private fun switchToFragment(fg: Fragment) {
        mFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fg)
            .commit()
    }

    protected abstract val title: String
    protected abstract val navItemsId: Int
    protected abstract val navItemMap: Map<Int, BaseFragment>

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawer.openDrawer(GravityCompat.START)
        }
        return true
    }
}