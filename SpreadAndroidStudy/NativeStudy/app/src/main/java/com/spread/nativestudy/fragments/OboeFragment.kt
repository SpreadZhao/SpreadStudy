package com.spread.nativestudy.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.spread.nativestudy.R
import com.spread.nativestudy.TAG

class OboeFragment : Fragment(), OnTouchListener {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_neon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView: TextView = view.findViewById(R.id.neon_text)
        textView.text = "Tap to play"
        this.rootView = view
    }

    override fun onResume() {
        super.onResume()
        if (createStream() != 0) {
            Log.e(TAG, "createStream failure")
            return
        }
        rootView.setOnTouchListener(this)
    }

    override fun onPause() {
        destroyStream()
        super.onPause()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v.id == R.id.oboe_framelayout) {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> playSound(true)
                MotionEvent.ACTION_UP -> playSound(false)
            }
        }
        return true
    }

    private external fun createStream(): Int
    private external fun destroyStream()
    private external fun playSound(enable: Boolean): Int
}