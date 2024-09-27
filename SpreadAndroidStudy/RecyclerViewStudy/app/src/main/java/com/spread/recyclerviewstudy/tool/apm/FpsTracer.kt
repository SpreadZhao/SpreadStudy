package com.spread.recyclerviewstudy.tool.apm

import android.os.SystemClock
import android.util.Log
import android.view.Choreographer
import java.lang.reflect.Field
import java.lang.reflect.Method

class FpsTracer {

    private var lastFrameNanos = -1L
    private var total = 0F
    private var lost = 0F

    private val choreographer = Choreographer.getInstance()
    private val mCallbackQueues: Array<Any> = getObjectField(choreographer, "mCallbackQueues")
    private val frameInfo =
        getObjectField<LongArray>(getObjectField(choreographer, "mFrameInfo"), "frameInfo")

    private val callbackRunnable = Runnable {
        val frameTimeMs = frameInfo[2] / 1000000
        val cost = SystemClock.uptimeMillis() - frameTimeMs
        Log.d("SpreadAPM", "callback cost: $cost")
    }

    init {
        init()
    }

    fun postNew() {
        val callback = Choreographer.FrameCallback { frameTimeNanos ->
            cal(lastFrameNanos, frameTimeNanos)
            lastFrameNanos = frameTimeNanos
        }
        Choreographer.getInstance().postFrameCallback(callback)
    }

    private fun cal(last: Long, now: Long) {
        val cost = (now - last) / 1000000
        if (cost > 16L) {
            lost++
        }
        total++
        Log.d("SpreadAPM", "cost: ${cost}ms, lost ratio: ${(lost / total) * 100}%")
    }

    private fun init() {
        Log.d("SpreadAPM", "frameInfo: $frameInfo")
        Log.d("SpreadAPM", "callbacks: $mCallbackQueues")
//    Log.d("SpreadAPM", "addCallbackLocked: $methodAddCallbackLocked")
    }

    fun postHiddenCallback() {
        if (mCallbackQueues.isEmpty()) {
            return
        }
        val firstCallback = mCallbackQueues[0]
        val methodAddCallbackLocked = getMethodFromInstance(
            firstCallback, "addCallbackLocked",
            Long::class.java, Object::class.java, Object::class.java
        )
        synchronized(this) {
            methodAddCallbackLocked?.let {
                it.invoke(mCallbackQueues[0], -1L, callbackRunnable, null)
            }
        }
    }

    private fun <T> getObjectField(instance: Any, name: String): T {
        val field = instance.javaClass.getDeclaredField(name)
        field.isAccessible = true
        return field.get(instance) as T
    }

    private fun <T> getHiddenObjectField(instance: Any, name: String): T {
        val metaGetMethod =
            Class::class.java.getDeclaredMethod("getDeclaredField", String::class.java)
        val hiddenField = metaGetMethod.invoke(instance::class.java, name) as Field
        hiddenField.isAccessible = true
        return hiddenField.get(instance) as T
    }

    private fun getMethodFromInstance(
        instance: Any,
        name: String,
        vararg argTypes: Class<*>
    ): Method {
        val method = instance.javaClass.getDeclaredMethod(name, *argTypes)
        method.isAccessible = true
        return method
    }
}