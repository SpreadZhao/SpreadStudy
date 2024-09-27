package com.example.customviewtest.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewtest.R
import com.example.customviewtest.customview.ChangeColorTextView

class DoFrameHookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_do_frame_hook)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val text = findViewById<ChangeColorTextView>(R.id.hook_color_text)
        text.text = "abcde"
        findViewById<Button>(R.id.hook_btn_invalidate).setOnClickListener {
            text.color = Color.YELLOW
        }
    }
}