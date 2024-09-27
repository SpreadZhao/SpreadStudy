package com.spread.nativestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.spread.nativestudy.databinding.ActivityMainBinding
import com.spread.nativestudy.fragments.OboeFragment
import com.spread.nativestudy.fragments.SimpleClassNameFragment
import com.spread.nativestudy.fragments.SimpleTimerFragment

class MainActivity : AppCompatActivity() {

    companion object {
        // Used to load the 'native-study' library on application startup.
        init {
            System.loadLibrary("native-study")
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var navView: NavigationView
    private var drawerOpened = false

    private val drawerListener = object : DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

        }

        override fun onDrawerOpened(drawerView: View) {
            drawerOpened = true
        }

        override fun onDrawerClosed(drawerView: View) {
            drawerOpened = false
        }

        override fun onDrawerStateChanged(newState: Int) {

        }

    }

    private val navListener = OnNavigationItemSelectedListener { item ->
        drawer.closeDrawers()
        val newFragment = when (item.itemId) {
            R.id.menu_item_simple_class_name -> SimpleClassNameFragment()
            R.id.menu_item_simple_timer -> SimpleTimerFragment()
            R.id.menu_item_oboe -> OboeFragment()
            else -> null
        }
        newFragment?.let { switchToFragment(it) }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        drawer = binding.root.findViewById(R.id.drawer_main)
        navView = binding.root.findViewById(R.id.main_nav)
        setContentView(binding.root)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_launcher_foreground)
        }
        drawer.addDrawerListener(drawerListener)
        navView.setNavigationItemSelectedListener(navListener)
    }

    private fun switchToFragment(fg: Fragment) {
        val fm = supportFragmentManager
        fm.beginTransaction()
                .replace(R.id.fragment_container, fg)
                .commit()
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