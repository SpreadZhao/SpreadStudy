package com.spread.nativestudy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.spread.nativestudy.R

class SimpleClassNameFragment : Fragment() {

    private var rootView: FrameLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_simple_class_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootView = view as? FrameLayout
        val textFromC = stringFromJNI()
        rootView?.findViewById<TextView>(R.id.simple_class_name_text)?.text = textFromC
    }

    /**
     * A native method that is implemented by the 'nativestudy' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String

    private fun stringFromJava(): String {
        return this.javaClass.name
    }
}