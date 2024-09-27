package com.spread.ostepstudy.chapter_fragment.introduction

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.spread.ostepstudy.R
import com.spread.ostepstudy.chapter_fragment.BaseFragment
import com.spread.ostepstudy.common.terminal.TerminalAdapter
import kotlin.concurrent.thread

class CPUFragment : BaseFragment("CPU"), View.OnClickListener {
    override val rootViewId: Int
        get() = R.layout.fragment_introduction_cpu

    private var adapter: TerminalAdapter? = null
    private lateinit var argumentEdit: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val startBtn = view.findViewById<Button>(R.id.cpu_btn)
        argumentEdit = view.findViewById(R.id.cpu_edit)
        adapter = view.findViewById<RecyclerView>(R.id.cpu_rv).adapter as? TerminalAdapter
        startBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            R.id.cpu_btn -> onStartClicked()
        }
    }

    private fun onStartClicked() {
        val msg = argumentEdit.text
        thread {
            while (true) {
                runInMain { adapter?.addMsg(msg.toString()) }
                spin(1)
            }
        }
    }

    private external fun spin(howlong: Int)
}