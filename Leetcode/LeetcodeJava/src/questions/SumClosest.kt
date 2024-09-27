package questions

import kotlin.math.abs

/**
 * Link: [Sum Closest](https://leetcode.com/problems/3sum-closest/)
 * Close to "Sum"
 */
class SumClosest {
    // -4 -1 1 2
    fun threeSumClosest(nums: IntArray, target: Int): Int {
        nums.sort()
        var sum = nums[0] + nums[1] + nums[2]
        for (i in 0 until nums.size - 2) {
            if (i > 0 && nums[i] == nums[i - 1]) continue
            var j = i + 1
            var k = nums.lastIndex
            while (j < k) {
                val curr = nums[i] + nums[j] + nums[k]
                if (curr == target) return curr
                if (abs(curr - target) < abs(target - sum)) sum = curr
                if (curr < target) j++
                else k--
            }
        }
        return sum
    }

    fun calDis(sum: Int, target: Int): Int {
        return abs(sum - target)
    }
}