package algo

class MergeSort {
    fun sort(array: IntArray) {
        core(array, 0, array.lastIndex)
    }

    private fun core(array: IntArray, low: Int, high: Int) {
        if (low < high) {
            val mid = (low + high) / 2
            core(array, low, mid)
            core(array, mid + 1, high)
            merge(array, low, mid, high)
        }
    }

    private fun merge(array: IntArray, low: Int, mid: Int, high: Int) {
        val left = array.copyOfRange(low, mid + 1) + intArrayOf(Int.MAX_VALUE)
        val right = array.copyOfRange(mid + 1, high + 1) + intArrayOf(Int.MAX_VALUE)
        var i = 0;
        var j = 0
        for (k in low..high) {
            if (left[i] < right[j]) {
                array[k] = left[i]
                i++
            } else {
                array[k] = right[j]
                j++
            }
        }
    }
}