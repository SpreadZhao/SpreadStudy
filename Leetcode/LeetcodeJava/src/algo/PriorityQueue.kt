package algo

class PriorityQueue(private val heap: MaxHeap) {

    val length
        get() = heap.length

    val isEmpty
        get() = heap.isEmpty

    fun enqueue(num: Int) {
        heap.add(Int.MIN_VALUE)
        update(heap.lastIndex, num)
    }

    // Upgrade the priority of the element indexed.
    fun update(index: Int, newValue: Int): Boolean {
        if (newValue < heap[index]) return false
        heap[index] = newValue
        var i = index
        while (i > heap.firstIndex && heap[i] > heap.parent(i)) {
            heap.swap(i, heap.parentIndex(i))
            i = heap.parentIndex(i)
        }
        return true
    }

    fun dequeue(): Int {
        if (isEmpty) return Int.MIN_VALUE
        heap.swap(heap.firstIndex, heap.lastIndex)
        val res = heap.last
        heap.eraseLast()
        heap.heapify(heap.firstIndex)
        return res
    }

    fun printHeap() {
        if (isEmpty) return
        for (i in heap.firstIndex..heap.lastIndex) {
            print("${heap[i]} ")
        }
        println()
    }

    fun print() {
        val tempQueue = PriorityQueue(heap.copy())
        while (!tempQueue.isEmpty) {
            print("${tempQueue.dequeue()} ")
        }
        println()
    }
}