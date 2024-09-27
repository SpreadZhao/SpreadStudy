package exercise

import kotlin.math.max

class LongestCommonSubsequence {

    fun longestCommonSubsequence3(str1: String, str2: String): Int {
        val dp = Array(str1.length + 1) { IntArray(str2.length + 1) { -1 } }
        return lcsdp(str1, str2, str1.length, str2.length, dp)
    }

    fun longestCommonSubsequence4(str1: String, str2: String): Int {
        val m = str1.length;
        val n = str2.length
        val dp = Array(m + 1) { IntArray(n + 1) }
        for (i in 0..m) {
            for (j in 0..n) {
                if (i == 0 || j == 0) dp[i][j] = 0
                else if (str1[i - 1] == str2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1
                else dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
            }
        }
        return dp[m][n]
    }

    private fun lcsdp(x: String, y: String, m: Int, n: Int, dp: Array<IntArray>): Int {
        if (m == 0 || n == 0) return 0
        if (dp[m][n] != -1) return dp[m][n]
        dp[m][n] = if (x[m - 1] == y[n - 1])
            lcsdp(x, y, m - 1, n - 1, dp) + 1
        else
            max(lcsdp(x, y, m, n - 1, dp), lcsdp(x, y, m - 1, n, dp))
        return dp[m][n]
    }

    private fun lcs(x: String, y: String, m: Int, n: Int): Int {
        if (m == 0 || n == 0) return 0
        return if (x[m - 1] == y[n - 1]) lcs(x, y, m - 1, n - 1) + 1
        else max(lcs(x, y, m, n - 1), lcs(x, y, m - 1, n))
    }

    fun longestCommonSubsequence2(str1: String, str2: String): Int {
        return lcs(str1, str2, str1.length, str2.length)
    }

    fun longestCommonSubsequence(str1: String, str2: String): Int {
        val subs1 = generateSubs(str1)
        val subs2 = generateSubs(str2)
        var lcs = ""
        for (sub1 in subs1) {
            for (sub2 in subs2) {
                if (sub1 == sub2 && sub1.length > lcs.length) lcs = sub1
            }
        }
        return lcs.length
    }

    private fun generateSubs(str: String): List<String> {
        val subs = ArrayList<String>()
        generateHelper(str, "", 0, subs)
        return subs
    }

    private fun generateHelper(str: String, sub: String, index: Int, subs: ArrayList<String>) {
        if (index == str.length) {
            subs.add(sub)
            return
        }
        generateHelper(str, sub, index + 1, subs)
        generateHelper(str, sub + str[index], index + 1, subs)
    }
}