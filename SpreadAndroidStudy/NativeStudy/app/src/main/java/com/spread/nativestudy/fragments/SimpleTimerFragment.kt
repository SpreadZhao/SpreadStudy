package com.spread.nativestudy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.spread.nativestudy.R

class SimpleTimerFragment : Fragment() {

    companion object {
        private var hour = 0
        private var min = 0
        private var second = 0
    }

    private lateinit var timeView: TextView
    private lateinit var descView: TextView
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_simple_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        timeView = view.findViewById(R.id.simple_timer_time)
        descView = view.findViewById(R.id.simple_timer_description)
        startBtn = view.findViewById(R.id.simple_timer_start_timer)
        stopBtn = view.findViewById(R.id.simple_timer_stop_timer)
        startBtn.setOnClickListener { startTimer() }
        stopBtn.setOnClickListener { stopTimer() }
    }

    override fun onResume() {
        super.onResume()
        clearTimer()
        descView.text = getDescription()
        requestForCurrTime()
    }

    private fun clearTimer() {
        hour = 0
        min = 0
        second = 0
    }

    private external fun getDescription(): String
    private external fun requestForCurrTime()

    private external fun startTimer()
    private external fun stopTimer()

    private external fun testRegister()

    /**
     * 由C++回调，设置当前时间。
     */
    @Keep
    private fun setCurrTime(time: String) {
        timeView.text = time
    }

    @Keep
    private fun updateTime() {
        ++second
        if (second >= 60) {
            ++min
            second = 0
            if (min >= 60) {
                ++hour
                min = 0
            }
        }
        val timeStr = "$hour:$min:$second"
        Log.i("SpreadNative", "updateTime: $timeStr")
        // 由C++调用，所以要在UI线程调用。
        activity?.runOnUiThread {
            timeView.text = timeStr
        }
    }


}