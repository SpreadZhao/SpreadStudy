package test

import algo.*

object TestAlgo {
    fun insertionSort() {
        val arr = intArrayOf(2, 3, 4, 5, 1)
        val arr2 = intArrayOf(31, 41, 59, 26, 41, 58)
        InsertionSort().sort(arr2)
        arr2.print()
    }

    fun mergeSort() {
        val arr = intArrayOf(5, 8, 6, 9, 7, 100, 23, 315, 12, 77, 55, 33)
        MergeSort().sort(arr)
        arr.print()
    }

    fun maximumSubarray() {
        val arr = intArrayOf(-2, 11, -4, 13, -5, -2)
        println(MaximumSubarray().maxSumDivideConquer(arr))
    }

    fun maxHeap() {
        val arr = intArrayOf(2, 5, 7, 1, 8, 13, 14)
        val arr2 = intArrayOf(9, 8, 6, 5, 7, 1, 0)
        val heap1 = MaxHeap().setArray(arr).build()
        val heap2 = MaxHeap().setArray(arr).buildRecursive()
        heap1.apply {
            print("heap1: ")
            print()
        }
        heap2.apply {
            print("heap2: ")
            print()
        }
        val pq = PriorityQueue(heap1)
        pq.update(5, 9)
        pq.apply {
            print("pq: ")
            print()
            print("pqHeap: ")
            printHeap()
        }
        pq.enqueue(12)
        pq.apply {
            println("after insertion:")
            print("pq: ")
            print()
            print("pqHeap: ")
            printHeap()
        }
    }

    fun quickSort() {
        val arr = intArrayOf(3, 56, 4, 6, 8, 4, 35, 33, 22, 66, 77, 2, 1, 6, 9)
        val arr2 = intArrayOf(100, 101, 2, 3, 5)
        QuickSort().sort(arr)
        QuickSort().sort2(arr2)
        arr.print()
        arr2.print()
    }

    fun countingSort() {
        val arr = intArrayOf(2, 5, 7, 1, 8, 13, 14)
        val arr2 = intArrayOf(4, 2, 3, 4, 3)
        CountingSort().sort(arr2, 2..4).print()
        CountingSort().sort(arr, 1..14).print()
    }

    fun rodCutting() {
        val profit = intArrayOf(0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30)
        val profit2 = intArrayOf(0, 1, 5, 8, 9, 10, 17, 17, 20, 24)
        val profit3 = intArrayOf(0, 1, 5, 8, 9, 10, 17, 17, 20)
        val profit4 = intArrayOf(0, 1, 5, 8, 9, 10, 17, 17)
        println(RodCutting().bestVal(profit))
        println(RodCutting().bestVal(profit2))
        println(RodCutting().bestVal(profit3))
        println(RodCutting().bestVal(profit4))
        println("dp:")
        println(RodCutting().bestVal2(profit))
        println(RodCutting().bestVal2(profit2))
        println(RodCutting().bestVal2(profit3))
        println(RodCutting().bestVal2(profit4))
    }

    private fun IntArray.print() {
        forEach {
            print("$it ")
        }
        println()
    }
}