package questions

import kotlin.math.max

/**
 * Link: [Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/)
 */

class LongestPalindromicSubsequence {
    fun longestPalindromeSubseq(s: String): Int {
        val n = s.length
        val dp = Array(n) { IntArray(n) }
        for (i in n - 1 downTo 0) {
            dp[i][i] = 1
            for (j in i + 1 until n) {
                if (s[i] == s[j]) dp[i][j] = dp[i + 1][j - 1] + 2
                else dp[i][j] = max(dp[i][j - 1], dp[i + 1][j])
            }
        }
        return dp[0][n - 1]
    }

    fun longestPalindromeSubseq2(s: String): Int {
        val n = s.length
        val dp = Array(n) { IntArray(n) }
        for (j in 0 until n) {
            dp[j][j] = 1
            for (i in j - 1 downTo 0) {
                if (s[i] == s[j]) dp[i][j] = dp[i + 1][j - 1] + 2
                else dp[i][j] = max(dp[i][j - 1], dp[i + 1][j])
            }
        }
        return dp[0][n - 1]
    }

}