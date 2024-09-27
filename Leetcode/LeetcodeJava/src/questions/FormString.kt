package questions

/**
 * Link: [Number of Ways to Form a Target String Given a Dictionary](https://leetcode.com/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/)
 */

class FormString {
    fun numWays(words: Array<String>, target: String): Int {
        val ALPHABET = 26
        val MOD = 1000000000L + 7
        val m = target.length
        val k = words[0].length
        val cnt = Array(ALPHABET) { IntArray(k) { 0 } }
        for (j in 0 until k) {
            for (str in words) {
                cnt[str[j] - 'a'][j]++
            }
        }
        val dp = Array(m + 1) { LongArray(k + 1) { 0 } }
        dp[0][0] = 1
        for (i in 0..m) {
            for (j in 0 until k) {
                if (i < m) {
                    dp[i + 1][j + 1] += cnt[target[i] - 'a'][j] * dp[i][j]
                    dp[i + 1][j + 1] %= MOD
                }
                dp[i][j + 1] += dp[i][j]
                dp[i][j + 1] %= MOD
            }
        }
        return dp[m][k].toInt()
    }
}