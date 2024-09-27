package questions

import kotlin.math.min

/**
 * https://leetcode.cn/problems/spiral-matrix/description/
 */
class SpiralMatrix {
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        var k = 0
        val res = ArrayList<Int>()
        val n = matrix.size
        val m = if (matrix.isNotEmpty()) matrix[0].size else -1
        val total = m * n
        val isVisited = BooleanArray(min(m, n)) { false }
        while (true) {
//            if (isVisited[k]) break
            var i = k;
            var j = k
            while (j < m - k) {
//                if (i == j) visit(isVisited, i)
                res.add(matrix[i][j++])
                if (res.size == total) return res
            }
            j--; i++
            while (i < n - k) {
//                if (i == j) visit(isVisited, i)
                res.add(matrix[i++][j])
                if (res.size == total) return res
            }
            i--; j--
            while (j >= k) {
//                if (i == j) visit(isVisited, i)
                res.add(matrix[i][j--])
                if (res.size == total) return res
            }
            j++; i--
            while (i > k) {
//                if (i == j) visit(isVisited, i)
                res.add(matrix[i--][j])
                if (res.size == total) return res
            }
            k++
        }
        return res
    }

    private fun visit(isVisited: BooleanArray, index: Int) {
        isVisited[index] = true
    }
}