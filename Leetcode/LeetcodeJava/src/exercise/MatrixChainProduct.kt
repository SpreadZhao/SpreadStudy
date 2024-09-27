package exercise

import kotlin.math.min

class MatrixChainProduct {
    fun minCount(p: IntArray, i: Int, j: Int): Int {
        if (i == j) return 0
        var min = Int.MAX_VALUE
        for (k in i until j) {
            val count = minCount(p, i, k) + minCount(p, k + 1, j) + p[i - 1] * p[k] * p[j]
            if (count < min) min = count
        }
        return min
    }

    fun minCount2(p: IntArray): Int {
        val i = 1
        val n = p.size
        val j = n - 1
        val dp = Array(n) { IntArray(n) { -1 } }
        return dpCore(p, i, j, dp)
    }

    fun minCount3(p: IntArray): Int {
        val n = p.size
        val dp = Array(n) { IntArray(n) { -1 } }
        val cut = Array(n) { IntArray(n) }
        for (i in 1 until n) dp[i][i] = 0
        for (l in 2 until n) {
            for (i in 1 until n - l + 1) {
                val j = i + l - 1
                // if (j == n) continue
                dp[i][j] = Int.MAX_VALUE
                for (k in i until j) {
                    val q = dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j]
                    if (q < dp[i][j]) {
                        dp[i][j] = q
                        cut[i][j] = k // Remember where I've cut in the best solution
                    }
                }
            }
        }
        return dp[1][n - 1]
    }

    private fun dpCore(p: IntArray, i: Int, j: Int, dp: Array<IntArray>): Int {
        if (i == j) return 0
        if (dp[i][j] != -1) return dp[i][j]
        dp[i][j] = Int.MAX_VALUE
        for (k in i until j) {
            dp[i][j] = min(dp[i][j], dpCore(p, i, k, dp) + dpCore(p, k + 1, j, dp) + p[i - 1] * p[k] * p[j])
        }
        return dp[i][j]
    }
}