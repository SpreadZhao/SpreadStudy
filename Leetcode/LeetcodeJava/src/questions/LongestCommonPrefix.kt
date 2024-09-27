package questions

/**
 * Link: [Longest Common Prefix](https://leetcode.com/problems/longest-common-prefix/description/)
 */
class LongestCommonPrefix {
    fun longestCommonPrefix(strs: Array<String>): String {
        val builder = StringBuilder()
        var j = 0
        while (j != strs[0].length) {
            val base = strs[0][j]
            for (i in 1 until strs.size) {
                if (j == strs[i].length || strs[i][j] != base) {
                    return builder.toString()
                }
            }
            j++
            builder.append(base)
        }
        return builder.toString()
    }

    fun longestCommonPrefix2(strs: Array<String>): String {
        strs.sort()
        val builder = StringBuilder();
        for (i in 0 until strs[0].length) {
            if (strs[0][i] != strs.last()[i]) break
            builder.append(strs[0][i])
        }
        return builder.toString()
    }
}