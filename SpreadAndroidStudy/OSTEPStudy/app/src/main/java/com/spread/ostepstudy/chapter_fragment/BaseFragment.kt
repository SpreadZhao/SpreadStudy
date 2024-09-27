package com.spread.ostepstudy.chapter_fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

abstract class BaseFragment(val subtitle: String) : Fragment() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(rootViewId, container, false)
        return rootView
    }

    fun runInMain(runnable: Runnable) {
        mHandler.post(runnable)
    }

    abstract val rootViewId: Int
}