package algo

import kotlin.math.max

class RodCutting {

    fun bestVal2(profit: IntArray): Int {
        if (profit.isEmpty()) return 0
        val dp = IntArray(profit.size) { Int.MIN_VALUE }
        val cut = IntArray(profit.size)
        dp[0] = 0
        for (j in 1 until profit.size) {
            for (i in 1..j) {
                if (dp[j] < profit[i] + dp[j - i]) {
                    dp[j] = profit[i] + dp[j - i]
                    cut[j] = i
                }
            }
        }
        return dp.last()
    }

    fun bestVal(profit: IntArray): Int {
        val n = profit.size - 1
        return bruceForce(profit, n)
    }

    private fun bruceForce(profit: IntArray, n: Int): Int {
        if (n == 0) return 0
        var res = Int.MIN_VALUE
        for (i in 1..n) {
            res = max(
                res,
                profit[i] + bruceForce(profit, n - i)
            )
        }
        return res
    }
}