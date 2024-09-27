package questions

import kotlin.math.max

/**
 * Link: [Minimize Maximum of Array](https://leetcode.com/problems/minimize-maximum-of-array/)
 */

class MinimizeMaximumOfArray {
    fun minimizeArrayValue(nums: IntArray): Int {
        var res = 0
        var sum = 0
        for (i in nums.indices) {
            sum += nums[i]
            val ceil = if (sum % (i + 1) == 0) sum / (i + 1)
            else sum / (i + 1) + 1
            res = max(res, ceil)
        }
        return res
    }

    fun minimizeArrayValue2(nums: IntArray): Int {
        var res = 0
        var sum = 0L
        for (i in nums.indices) {
            sum += nums[i]
            val ceil = if (sum % (i + 1) == 0L) sum / (i + 1)
            else sum / (i + 1) + 1
            res = max(res, ceil.toInt())
        }
        return res
    }


}