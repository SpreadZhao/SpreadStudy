package com.example.picphone

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.picphone.people.Persons
import com.example.picphone.shake.ShakeDetector
import com.example.picphone.shake.ShakeOpenSettings
import com.example.picphone.util.Caller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PPMain"
    }

    private val caller = Caller(this)

    private val detector = ShakeDetector()

    private val shakeListener = ShakeOpenSettings(this)

    private var sensorManager: SensorManager? = null

    private var accelerometer: Sensor? = null

    private var vibratorManager: VibratorManager? = null

    private var vibrator: Vibrator? = null

    private var adapter: PicAdapter? = null

    private var isInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        initForShakeDetect()
        detector.setShakeListener(shakeListener)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            shakeListener.configVibrator(vibratorManager)
        } else {
            shakeListener.setVibrator(vibrator)
        }
        val pager = findViewById<ViewPager2>(R.id.pager)
        this.adapter = PicAdapter(Persons.list, caller)
        pager.adapter = adapter
        loadSavedPersons()
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
        sensorManager?.registerListener(detector, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(detector)
        detector.clear()
    }

    private fun loadSavedPersons() {
        lifecycleScope.launch(context = Dispatchers.IO) {
            Persons.loadPersons(this@MainActivity)
            withContext(Dispatchers.Main) { adapter?.notifyDataSetChanged() }
            isInit = false
        }
    }

    private fun initForShakeDetect() {
        if (sensorManager == null) {
            sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        if (vibratorManager == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            } else {
                vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
        }
    }
}