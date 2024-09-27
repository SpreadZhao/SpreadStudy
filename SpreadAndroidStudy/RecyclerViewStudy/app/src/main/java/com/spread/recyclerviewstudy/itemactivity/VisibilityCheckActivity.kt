package com.spread.recyclerviewstudy.itemactivity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.spread.common.tool.UITools
import com.spread.common.visible.CalTextView
import com.spread.common.visible.VisibleChecker
import com.spread.recyclerviewstudy.R

class VisibilityCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_visibility_check)
        val mainView = findViewById<ViewGroup>(R.id.main)
        val checker = VisibleChecker(object : VisibleChecker.ICheckEE {
            override val rootView: View
                get() = mainView
            override val totalWidth: Int
                get() = UITools.getRealScreenWidth(this@VisibilityCheckActivity)
            override val totalHeight: Int
                get() = UITools.getRealScreenHeight(this@VisibilityCheckActivity)
            override val expectedTime = 3000L
            override val validRatio: Float
                get() = 0.3f
        })
        checker.start()
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<CalTextView>(R.id.visi_3).setOnClickListener {
            it.requestLayout()
        }
    }
}