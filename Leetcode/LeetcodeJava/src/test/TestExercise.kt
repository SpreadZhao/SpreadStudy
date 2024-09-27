package test

import exercise.*

object TestExercise {
    fun testGraph() {
        val n = 6
        val edges = arrayOf(
            intArrayOf(1, 1, 0, 0, 0, 1),
            intArrayOf(1, 1, 1, 0, 1, 0),
            intArrayOf(0, 1, 1, 1, 0, 0),
            intArrayOf(0, 0, 1, 1, 1, 0),
            intArrayOf(0, 1, 0, 1, 1, 0),
            intArrayOf(1, 0, 0, 0, 0, 1)
        )
        val graph = MatrixGraph(n, GraphType.UDG, edges)
        MatrixGraph.DFS(graph, 0)
    }

    fun matrixChainProduct() {
        val arr = intArrayOf(40, 20, 30, 10, 30)
        val arr2 = intArrayOf(5, 10, 3, 12, 5, 50, 6)
        val arr3 = intArrayOf(30, 35, 15, 5, 10, 20, 25)
        println(MatrixChainProduct().minCount(arr2, 1, arr2.size - 1))
        println(MatrixChainProduct().minCount2(arr2))
        println(MatrixChainProduct().minCount3(arr2))
        println(MatrixChainProduct().minCount3(arr3))
    }

    fun longestCommonSubsequence() {
        val s1 = "10010101"
        val s2 = "010110110"
        println(LongestCommonSubsequence().longestCommonSubsequence4(s1, s2))
    }

    fun longestCommonSubstring() {
        val x = "abcdxyz"
        val y = "xyzabcd"
        println(LongestCommonSubstring().lcs(x, y, x.length, y.length))
    }

    fun maxSum() {
        val arr = intArrayOf(-2, 11, -4, 13, -5, -2)
        val arr2 = intArrayOf(1)
        println(MaxSum().maxSum(arr))
    }

    fun fractionalKS() {
        val arr = arrayOf(
            KnapSack.ItemValue(60, 10),
            KnapSack.ItemValue(100, 20),
            KnapSack.ItemValue(120, 30)
        )
        val capacity = 50
        println(KnapSack().getMaxValue(arr, capacity))
    }

    fun zeroOneKS() {
        val profit = intArrayOf(20, 30, 65, 40, 60)
        val weight = intArrayOf(10, 20, 30, 40, 50)
        val w = 100
        println(KnapSack().zeroOneKnap4(w, weight, profit, profit.size))
    }

    fun schedule() {
        val time = intArrayOf(15, 8, 3, 10)
        println(Schedule().minAverageCompletion(time))
    }

    fun shortest() {
        val I = Int.MAX_VALUE
        val edges = arrayOf(
            intArrayOf(0, -1, 3, I, I),
            intArrayOf(I, 0, 3, 2, 2),
            intArrayOf(I, I, 0, I, I),
            intArrayOf(I, 1, 5, 0, I),
            intArrayOf(I, I, I, -3, 0)
        )
        val edges2 = arrayOf(
            intArrayOf(0, 6, I, 4, I),
            intArrayOf(I, 0, 5, 8, -4),
            intArrayOf(I, -2, 0, I, I),
            intArrayOf(I, I, -3, 0, 9),
            intArrayOf(2, I, 7, I, 0)
        )
        println("before:")
        edges2.forEach {
            it.forEach { num ->
                if (num != I) print("$num\t")
                else print("I\t")
            }
            println()
        }
        println("bellman-ford:")
        ShortestPath().shortestPath(edges2)
//        println()
//        ShortestPath().floyd(link = edges2)
//        println("floyd:")
//        edges2.forEach {
//            it.forEach { num ->
//                if (num != I) print("$num\t")
//                else print("I\t")
//            }
//            println()
//        }
    }

    fun shortest2() {
        val I = Int.MAX_VALUE
        val edges = arrayOf(
            intArrayOf(0, 3, 8, I, -4),
            intArrayOf(I, 0, I, 1, 7),
            intArrayOf(I, 4, 0, I, I),
            intArrayOf(2, I, -5, 0, I),
            intArrayOf(I, I, I, 6, 0)
        )
        val edges2 = arrayOf(
            intArrayOf(0, I, I, I, -1, I),
            intArrayOf(1, 0, I, 2, I, I),
            intArrayOf(I, 2, 0, I, I, -8),
            intArrayOf(-4, I, I, 0, 3, I),
            intArrayOf(I, 7, I, I, 0, I),
            intArrayOf(I, 5, 10, I, I, 0)
        )
        ShortestPath().fasterAllPairsShortest(edges2)
//        println("floyd:")
//        ShortestPath().floyd(edges2)

    }

    fun queen8() {
        println(Queen8().run())
    }

    fun printThread() {
        PrintThread().start()
    }
}