package algo

class MaximumSubarray {
    fun maxSumDivideConquer(array: IntArray): Int {
        return coreDC(array, 0, array.lastIndex)
    }

    private fun coreDC(array: IntArray, low: Int, high: Int): Int {
        return if (low == high) array[low]
        else {
            val mid = (low + high) / 2
            val leftMaxSum = coreDC(array, low, mid)
            val rightMaxSum = coreDC(array, mid + 1, high)
            val crossMaxSum = coreDCCrossing(array, low, mid, high)
            maxOf(leftMaxSum, rightMaxSum, crossMaxSum)
        }
    }

    private fun coreDCCrossing(array: IntArray, low: Int, mid: Int, high: Int): Int {
        var leftMaxSum = Int.MIN_VALUE
        var sum = 0
        for (i in mid downTo low) {
            sum += array[i]
            if (sum > leftMaxSum) leftMaxSum = sum
        }
        var rightMaxSum = Int.MIN_VALUE
        sum = 0
        for (j in mid + 1..high) {
            sum += array[j]
            if (sum > rightMaxSum) rightMaxSum = sum
        }
        return leftMaxSum + rightMaxSum
    }
}