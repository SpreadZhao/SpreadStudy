package exercise

import kotlin.math.abs

class Queen8(private var count: Int = 0) {
    companion object {
        private const val MAX = 8
    }

    private val arr = IntArray(MAX) { Int.MIN_VALUE }
    private val mockArr = Array(MAX) { IntArray(MAX) { 0 } }
    fun run(): Int {
        putQueen(0)
        return count
    }

    private fun putQueen(n: Int) {
        if (n == MAX) {
            count++
            mock()
            return
        }
        for (i in arr.indices) {
            arr[n] = i
//            mock()
            if (noCollision(n)) putQueen(n + 1)
        }
    }

    private fun noCollision(n: Int): Boolean {
        for (i in 0 until n) {
            if (isSameColumn(i, n) || isSameBias(i, n)) return false
        }
        return true
    }

    private fun isSameColumn(i1: Int, i2: Int) = arr[i1] == arr[i2]
    private fun isSameBias(i1: Int, i2: Int) = abs(i1 - i2) == abs(arr[i1] - arr[i2])

    private fun mock() {
        for (i in arr.indices) {
            if (arr[i] != Int.MIN_VALUE) mockArr[i][arr[i]] = 1
        }
        mockArr.forEach {
            it.forEach { num ->
                print("$num ")
            }
            println()
        }
        println("---------------------")
        mockArr.forEach {
            it.fill(0)
        }
    }
}