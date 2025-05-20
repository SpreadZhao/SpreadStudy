package com.spread.androidstudy.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.spread.androidstudy.R
import com.spread.androidstudy.dp

class TwiceXYBugFragment : BaseFragment(R.layout.fragment_twice_xy_bug) {

    private var colorView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (view is FrameLayout) {
            view.addView(
                getColorView(),
                FrameLayout.LayoutParams(
                    100.dp,
                    100.dp,
                    Gravity.END
                )
            )
            getColorView().visibility = View.GONE
        }
        val moveButton = view.findViewById<View>(R.id.move_btn)
        moveButton.setOnClickListener {
            getColorView().run {
                x = 100.dp.toFloat()
                y = 100.dp.toFloat()
            }
            getColorView().visibility = View.VISIBLE
            Log.d("TwiceXYBug", "gravity: ${(getColorView().layoutParams as FrameLayout.LayoutParams).gravity}")
        }
    }


    private fun getColorView(): View {
        if (colorView != null) {
            return colorView!!
        }
        colorView = View(requireContext())
        colorView!!.setBackgroundColor(Color.RED)
        return colorView!!
    }

}