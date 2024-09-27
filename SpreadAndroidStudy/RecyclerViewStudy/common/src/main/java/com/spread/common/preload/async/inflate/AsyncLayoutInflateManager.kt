package com.spread.common.preload.async.inflate

import android.annotation.SuppressLint
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.LayoutInflaterCompat
import com.spread.common.GlobalApplication
import com.spread.common.preload.async.data.PreloadViewInfo
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch

@SuppressLint("StaticFieldLeak")
object AsyncLayoutInflateManager : IAsyncLayoutInflateService {

    private lateinit var mContextThemeWrapper: ContextThemeWrapper

    private lateinit var mLayoutInflater: AsyncLayoutInflater

    lateinit var asyncInflateDepend: IAsyncInflateDepend

    private val mCountDownLatch = CountDownLatch(1)

    private val mViewPool = ConcurrentHashMap<Int, List<View>>()

    @Volatile
    private var mStopPreload = false

    private var mInterruptPreload = false

    fun init(depend: IAsyncInflateDepend) {
        this.asyncInflateDepend = depend
        mContextThemeWrapper =
            ContextThemeWrapper(GlobalApplication.application, asyncInflateDepend.themeResId)
        mLayoutInflater = AsyncLayoutInflater(mContextThemeWrapper)
    }

    override val context: Context
        get() = mContextThemeWrapper


    override fun inflate(info: PreloadViewInfo, callback: OnInflateFinishListener) {
        mLayoutInflater.inflate(info, FrameLayout(mContextThemeWrapper), callback)
    }

    fun inflateMain(resource: Int, root: ViewGroup, context: Context): View {
        val view = getOriginInflater(context).inflate(resource, root, false)
        return view
    }

    private fun getOriginInflater(context: Context): LayoutInflater {
        val oldInflater = LayoutInflater.from(context)
        val newInflater = oldInflater.cloneInContext(oldInflater.context) ?: return oldInflater
        LayoutInflaterCompat.setFactory2(newInflater, asyncInflateDepend.layoutInflaterFactory)
        return newInflater
    }
}