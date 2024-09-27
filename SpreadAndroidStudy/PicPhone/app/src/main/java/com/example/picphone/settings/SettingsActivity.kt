package com.example.picphone.settings

import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picphone.R
import com.example.picphone.people.Persons
import kotlinx.coroutines.launch
import java.io.IOException

class SettingsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SettingsActivity"
    }


    private val adapter = SettingsAdapter(Persons.list, this)
    private var recyclerView: RecyclerView? = null
    private var addButton: Button? = null
    private var saveButton: Button? = null
    private var clearButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.recyclerView = findViewById(R.id.settings_recyclerview)
        this.addButton = findViewById(R.id.button_add)
        this.saveButton = findViewById(R.id.button_save)
        this.clearButton = findViewById(R.id.button_clear)
        prepare()
    }

    private fun prepare() {
        // recyclerview
        val rv = this.recyclerView ?: return
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(this, RecyclerView.VERTICAL)
        rv.addItemDecoration(divider)
        rv.adapter = this.adapter
        // buttons
        saveButton?.setOnClickListener {
            Persons.saveImages(context = this)
            lifecycleScope.launch {
                Persons.savePersons(context = this@SettingsActivity)
            }
        }
        addButton?.setOnClickListener {
            Persons.addPerson("请输入电话号", null)
            adapter.notifyItemInserted(Persons.list.lastIndex)
        }
        clearButton?.setOnClickListener { Persons.clear(context = this, owner = this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val dataString = data.dataString
            Log.d(TAG, "uri: $dataString")
            val uri = data.data ?: return
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
                } else {
                    MediaStore.Images.Media.getBitmap(contentResolver, uri)
                }
                val drawable = BitmapDrawable(resources, bitmap)
                adapter.changePicture(drawable)
            } catch (_: IOException) {}
        }
    }
}