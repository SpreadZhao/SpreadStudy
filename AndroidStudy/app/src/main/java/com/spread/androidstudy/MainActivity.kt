package com.spread.androidstudy

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.spread.androidstudy.fragment.DefaultDrawerListener
import com.spread.androidstudy.fragment.FragmentHolder

class MainActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private var drawerOpened = false

    private val fragmentHolder = FragmentHolder()

    private val drawerListener = object : DefaultDrawerListener {
        override fun onDrawerOpened(drawerView: View) {
            drawerOpened = true
        }

        override fun onDrawerClosed(drawerView: View) {
            drawerOpened = false
        }
    }

    private val navListener = OnNavigationItemSelectedListener { item ->
        drawer.closeDrawers()
        fragmentHolder.getFragment(item.itemId)?.apply(::switchToFragment)
        true
    }

    private fun switchToFragment(fragment: Fragment) = supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, fragment)
        .commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        drawer = findViewById(R.id.main)
        navView = findViewById(R.id.main_nav)
        navView.measuredState
        navView.post {  }
        Log.d(SpreadStudy.LOG_TAG, "navView width: ${navView.width}")
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.menu)
        }
        drawer.addDrawerListener(drawerListener)
        navView.setNavigationItemSelectedListener(navListener)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (!drawerOpened) {
                    drawer.openDrawer(GravityCompat.START)
                } else {
                    drawer.closeDrawer(GravityCompat.START)
                }
            }
        }
        return true
    }
}