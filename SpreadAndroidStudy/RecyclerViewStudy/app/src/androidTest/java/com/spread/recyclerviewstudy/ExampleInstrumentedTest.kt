package com.spread.recyclerviewstudy

import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Field
import java.lang.reflect.Method
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
        assertEquals("com.spread.recyclerviewstudy", appContext.packageName)
        val handler = Handler(Looper.getMainLooper())
        thread {
            Looper.prepare()
            handler.post {
                val choreographer = Choreographer.getInstance()
                val callbacks = getObjectField<Array<Any>>(choreographer, "mCallbackQueues")
                val frameInfo = getObjectField<Any>(choreographer, "mFrameInfo")
                println()
            }
        }
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
}