package questions

/**
 * Link: [Zigzag Conversion](https://leetcode.com/problems/zigzag-conversion/)
 */
class ZigzagConversion {
    fun convert(s: String, numRows: Int): String {
        if (numRows == 1) return s
        val bu = StringBuilder()
        for (i in 0 until numRows) {
            var isSlant = false
            var repeat = false
            var j = i
            var span: Int
            while (checkBoundary(s, j)) {
                if (!repeat) bu.append(s[j])
                if (isSlant) {
                    span = calSpan(i + 1)
                    repeat = span == 0
                    j += span
                    isSlant = false
                } else {
                    span = calSpan(numRows - i)
                    repeat = span == 0
                    j += span
                    isSlant = true
                }
            }
        }
        return bu.toString()
    }

    fun calSpan(row: Int): Int {
        return 2 * row - 2
    }

    fun checkBoundary(s: String, index: Int): Boolean {
        return index >= 0 && index < s.length
    }
}