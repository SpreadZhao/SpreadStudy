package com.spread.ottotts

import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum
import com.github.houbb.pinyin.util.PinyinHelper
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun pinyin() {
        val str = "你好，我是Spread Zhao，是一名 Android 开发者。"
        val pinyin = PinyinHelper.toPinyin(str, PinyinStyleEnum.NORMAL)
        println(pinyin)
    }
}