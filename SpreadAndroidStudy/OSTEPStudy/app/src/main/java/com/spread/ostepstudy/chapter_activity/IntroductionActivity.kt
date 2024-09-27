package com.spread.ostepstudy.chapter_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.spread.ostepstudy.R
import com.spread.ostepstudy.chapter_fragment.BaseFragment
import com.spread.ostepstudy.chapter_fragment.introduction.CPUFragment

class IntroductionActivity : ChapterBaseActivity() {
    override val navItemsId: Int
        get() = R.menu.nav_introduction
    override val title: String
        get() = "Introduction"
    override val navItemMap: Map<Int, BaseFragment>
        get() = mapOf(
            R.id.menu_item_intro_cpu to CPUFragment()
        )
}