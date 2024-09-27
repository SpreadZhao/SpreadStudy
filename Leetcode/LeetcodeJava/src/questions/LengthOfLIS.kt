package questions

import kotlin.math.max

/**
 * https://leetcode.cn/problems/longest-increasing-subsequence/solutions/147667/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/
 */
class LengthOfLIS {
    fun lengthOfLIS(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        val dp = IntArray(nums.size)
        dp[0] = 1
        var res = 1
        for (i in 1 until nums.size) {
            dp[i] = 1
            for (j in 0 until i) {
                if (nums[i] > nums[j]) {
                    dp[i] = max(dp[i], dp[j] + 1)
                }
            }
            res = max(res, dp[i])
        }
        return res
    }
}