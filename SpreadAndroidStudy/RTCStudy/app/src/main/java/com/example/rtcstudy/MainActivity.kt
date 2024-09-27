package com.example.rtcstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.rtcstudy.databinding.ActivityMainBinding
import com.example.rtcstudy.fragment.HelloRTCFragment
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navListener = OnNavigationItemSelectedListener { item ->
        val fragment = when (item.itemId) {
            R.id.menu_item_hello_rtc -> HelloRTCFragment()
            else -> null
        }
        fragment?.let { switchToFragment(it) }
        binding.drawerMain.closeDrawers()
        true
    }
    private val mFragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainActionBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground)
        binding.mainNav.setNavigationItemSelectedListener(navListener)
    }

    private fun switchToFragment(fg: Fragment) {
        mFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fg)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerMain.openDrawer(GravityCompat.START)
                return true
            }
        }
        return false
    }

    /**
     * A native method that is implemented by the 'rtcstudy' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("rtc-study")
        }
    }
}