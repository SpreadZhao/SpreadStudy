package algo

class CountingSort {
    fun sort(arr: IntArray, range: IntRange): IntArray {
        val res = IntArray(arr.size)
        val location = IntArray(range.last + 1) { 0 }
        for (j in arr.indices) location[arr[j]]++
        for (i in 2..range.last) location[i] += location[i - 1]
        for (j in arr.lastIndex downTo 0) {
            res[location[arr[j]] - 1] = arr[j]
            location[arr[j]]--
        }
        return res
    }
}