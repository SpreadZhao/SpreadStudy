package algo

class MaxHeap(
    private val arr: ArrayList<Int> = ArrayList<Int>().apply {
        add(HEAP_HEAD)
    }
) {

    companion object {
        //        private const val HEAP_MAX_SIZE = 100
        private const val INDEX_NOT_EXIST = -1
        private const val NODE_NOT_EXIST = -2
        private const val HEAP_HEAD = Int.MIN_VALUE
    }

    val length
        get() = arr.size - 1

    val firstIndex
        get() = if (length > 0) 1 else NODE_NOT_EXIST
    val lastIndex
        get() = length

    val first
        get() = arr[firstIndex]

    val last
        get() = arr[lastIndex]

    operator fun get(i: Int) = arr[i]

    operator fun set(index: Int, value: Int) {
        arr[index] = value
    }

    fun setArray(array: IntArray) = apply {
        arr.addAll(array.asList())
    }

    // 为什么从一半开始？因为只有一半结点有子节点啊!
    fun build() = apply {
        val startIndex = lastIndex / 2
        for (i in startIndex downTo 1) {
            heapify(i)
        }
    }

    fun buildRecursive() = apply {
        val startIndex = lastIndex / 2
        for (i in startIndex downTo 1) {
            heapifyRecursive(i)
        }
    }


    fun heapify(i: Int) {
        var index = i
        while (index < lastIndex) {
            val maxIndex = findMaxOfThree(index)

            // Parent is the largest
            if (index == maxIndex) break

            swap(index, maxIndex)
            index = maxIndex
        }
    }

    fun heapifyRecursive(i: Int) {
        val maxIndex = findMaxOfThree(i)
        // Parent is not the largest
        if (i != maxIndex) {
            swap(i, maxIndex)
            heapifyRecursive(maxIndex)
        }
    }

    private fun findMaxOfThree(start: Int): Int {
        var maxIndex = start
        val leftIndex = leftChildIndex(maxIndex)
        val rightIndex = rightChildIndex(maxIndex)
        if (nodeExist(leftIndex) && arr[maxIndex] < arr[leftIndex]) {
            maxIndex = leftIndex
        }
        if (nodeExist(rightIndex) && arr[maxIndex] < arr[rightIndex]) {
            maxIndex = rightIndex
        }
        return maxIndex
    }

    fun add(num: Int) {
        arr.add(num)
    }

    fun print() {
        for (i in 1..arr.lastIndex) print("${arr[i]} ")
        println()
    }

    fun leftChild(index: Int) = if (leftChildIndex(index) != INDEX_NOT_EXIST) arr[leftChildIndex(index)]
    else NODE_NOT_EXIST

    fun rightChile(index: Int) = if (rightChildIndex(index) != INDEX_NOT_EXIST) arr[rightChildIndex(index)]
    else NODE_NOT_EXIST

    fun parent(index: Int) = if (parentIndex(index) != INDEX_NOT_EXIST) arr[parentIndex(index)]
    else NODE_NOT_EXIST

    fun eraseLast() = arr.removeAt(lastIndex)

    fun copy(): MaxHeap {
        val copyArr = ArrayList(arr)
        return MaxHeap(copyArr)
    }

    val isEmpty
        get() = length == 0

    private fun leftChildIndex(index: Int) =
        if (2 * index <= lastIndex) 2 * index else INDEX_NOT_EXIST

    private fun rightChildIndex(index: Int) =
        if (2 * index + 1 <= lastIndex) 2 * index + 1 else INDEX_NOT_EXIST

    fun parentIndex(index: Int) =
        if (nodeExist(index / 2)) index / 2 else INDEX_NOT_EXIST

    private fun nodeExist(index: Int) = index in 1..lastIndex

    internal fun swap(index1: Int, index2: Int): Boolean {
        if (!nodeExist(index1) || !nodeExist(index2)) return false
        val temp = arr[index1]
        arr[index1] = arr[index2]
        arr[index2] = temp
        return true
    }
}