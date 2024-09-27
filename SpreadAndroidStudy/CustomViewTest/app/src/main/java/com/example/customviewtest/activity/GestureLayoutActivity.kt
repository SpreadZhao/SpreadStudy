package com.example.customviewtest.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewtest.R
import com.example.customviewtest.customview.gesture.GestureResizeLayout

class GestureLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gesture_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val layout = findViewById<GestureResizeLayout>(R.id.gesture_layout)
        val img = findViewById<ImageView>(R.id.test_pic)
        layout.mChild = img
        val spinner = findViewById<Spinner>(R.id.rotate_speed_spinner)
        val adapter = ArrayAdapter(this, R.layout.layout_rotate_speed_spinner, listOf(
            "Slow", "Normal", "Fast"
        ))
        spinner.adapter = adapter
        spinner.setSelection(1)
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> layout.speed = GestureResizeLayout.Speed.SLOW
                    1 -> layout.speed = GestureResizeLayout.Speed.NORMAL
                    2 -> layout.speed = GestureResizeLayout.Speed.FAST
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }
}