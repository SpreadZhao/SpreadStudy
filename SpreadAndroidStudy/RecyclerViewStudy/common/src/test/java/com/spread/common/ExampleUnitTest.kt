package com.spread.common

import com.spread.common.visible.VisibleChecker
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.max
import kotlin.math.min

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private fun calAreaIntersectionSize(a1: VisibleChecker.Area, a2: VisibleChecker.Area): Int {
        val left1 = a1.origin.x
        val right1 = left1 + a1.width
        val left2 = a2.origin.x
        val right2 = left2 + a2.width
        val top1 = a1.origin.y
        val bottom1 = top1 + a1.height
        val top2 = a2.origin.y
        val bottom2 = top2 + a2.height
        if (right2 <= left1 || left2 >= right1 || bottom2 <= top1 || top2 >= bottom1) {
            return 0
        }
        val left = max(left1, left2)
        val right = min(right1, right2)
        val top = max(top1, top2)
        val bottom = min(bottom1, bottom2)
        return (right - left) * (bottom - top)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testAreaIntersect() {
        val area1 = VisibleChecker.Area(
            VisibleChecker.Point(0, 0),
            100, 200
        )
        val area2 = VisibleChecker.Area(
            VisibleChecker.Point(60, 70),
            300, 130
        )
        println("size: ${calAreaIntersectionSize(area1, area2)}")
    }
}