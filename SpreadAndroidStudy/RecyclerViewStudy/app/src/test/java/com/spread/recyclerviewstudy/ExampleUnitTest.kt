package com.spread.recyclerviewstudy

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.reflect.Field

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val people = People()
        val field2 = getObjectField<Int>(people, "val2")
        println("field2: $field2")
        val field1 = getObjectField<Int>(people, "val1")
        println("field1: $field1")
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