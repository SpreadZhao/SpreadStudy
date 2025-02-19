package com.spread.androidstudy.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.spread.androidstudy.R
import com.spread.androidstudy.studies.livedata.UserViewModel

class LiveDataFragment : BaseFragment(R.layout.fragment_livedata) {

    private var observerAdded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val getUserBtn = view.findViewById<Button>(R.id.get_user)
        val addObserverBtn = view.findViewById<Button>(R.id.add_observer)
        val viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.userCount.observe(viewLifecycleOwner) {
            Log.i("Study-LiveData", "[o1] user count: $it")
        }
        getUserBtn.setOnClickListener {
            viewModel.addUser()
        }
        addObserverBtn.setOnClickListener {
            if (!observerAdded) {
                observerAdded = true
                viewModel.userCount.observe(viewLifecycleOwner) {
                    Log.i("Study-LiveData", "[o2] user count: $it")
                }
            }
        }
    }

}