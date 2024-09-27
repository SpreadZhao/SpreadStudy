package questions

/**
 * Link: [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)
 *
 *
 * My record is so poor!!!
 */
class LongestPalindromicSubstring {
    fun longestPalindrome(s: String): String {
        var res = s.substring(0, 1)
        for (i in s.indices) {
            for (len in 1..s.length - i) {
                if (s[i] == s[i + len - 1]) { // Time cost , speed up a lot!!!
                    if (len > res.length) {
                        if (isPalindromic(s, i, i + len)) {
                            res = s.substring(i, i + len)
                        }
                    }
                }
            }
        }
        return res
    }

    fun longestPalindrome2(s: String): String {
        var maxStr = ""
        if (s.isEmpty()) return maxStr
        val isPalindrome = Array(s.length) { BooleanArray(s.length) }
        for (j in s.indices) {
            for (i in j downTo 0) {
                val judge = s[i] == s[j]
                isPalindrome[i][j] = if (j - i > 2) isPalindrome[i + 1][j - 1] && judge
                else judge
                if (isPalindrome[i][j] && j - i + 1 > maxStr.length) {
                    maxStr = s.substring(i, j + 1)
                }
            }
        }
        return maxStr
    }

    // j - i - 1 >= 2 --> j - i >= 3 --> j - i > 2

    fun isPalindromic(s: String, start: Int, end: Int): Boolean {
        val sub = s.substring(start, end)
        val pal = StringBuilder()
        for (i in end - 1 downTo start) {
            pal.append(s[i])
        }
        return sub == pal.toString()
    }
}