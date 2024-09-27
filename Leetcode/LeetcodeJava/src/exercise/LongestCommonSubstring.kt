package exercise

import kotlin.math.max

class LongestCommonSubstring {
    fun lcs(x: String, y: String, m: Int, n: Int): Int {
        val dp = Array(m + 1) { IntArray(n + 1) }
        var res = 0
        for (i in 0..m) {
            for (j in 0..n) {
                if (i == 0 || j == 0) dp[i][j] = 0
                else if (x[i - 1] == y[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1
                    res = max(res, dp[i][j])
                } else
                    dp[i][j] = 0
            }
        }
        return res
    }
}