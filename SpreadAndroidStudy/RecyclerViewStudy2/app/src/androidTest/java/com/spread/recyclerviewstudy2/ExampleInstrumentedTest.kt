package com.spread.recyclerviewstudy2

import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import kotlin.concurrent.thread

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.spread.recyclerviewstudy2", appContext.packageName)
    thread {
      Looper.prepare()
      Handler(Looper.getMainLooper()).post {
        val choreographer = Choreographer.getInstance()
        val callbacks = getObjectField<Array<Any>>(choreographer, "mCallbackQueues")
        val frameInfo = ReflectUtil.getHiddenField<Any>(choreographer, "mFrameInfo")
        println()
      }
    }
  }

  private fun <T> getObjectField(instance: Any, name: String): T {
    val field = instance.javaClass.getDeclaredField(name)
    field.isAccessible = true
    return field.get(instance) as T
  }
}