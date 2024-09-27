package questions

import java.math.BigInteger

/**
 * Link: [Count Unreachable Pairs of Nodes in an Undirected Graph](https://leetcode.com/problems/count-unreachable-pairs-of-nodes-in-an-undirected-graph/description/)
 */
class UnreachableNodes {
    fun countPairs(n: Int, edges: Array<IntArray>): Long {
        val neighbors = HashMap<Int, ArrayList<Int>>()
        for (edge in edges) {
            if (!neighbors.containsKey(edge[0])) neighbors[edge[0]] = ArrayList()
            neighbors[edge[0]]?.add(edge[1])
            if (!neighbors.containsKey(edge[1])) neighbors[edge[1]] = ArrayList()
            neighbors[edge[1]]?.add(edge[0])
        }

        val visited = ArrayList<Boolean>()

        repeat(n) {
            visited.add(false)
        }

        var res = BigInteger("0")
        var remain = n

        for (i in 0 until n) {
            if (!visited[i]) {
                val islandCount = DFS_Core(i, neighbors, visited)
                res += (islandCount).toBigInteger() * (remain - islandCount).toBigInteger()
                remain -= islandCount
            }
        }

//        var res: Long = 0
//
//        for(i in 0 until islandNodeCount.size){
//            for(j in i + 1 until islandNodeCount.size){
//                res += islandNodeCount[i] * islandNodeCount[j]
//            }
//        }

        return res.toLong()

    }

    private fun DFS_Core(index: Int, neighbors: HashMap<Int, ArrayList<Int>>, visited: ArrayList<Boolean>): Int {
        visited[index] = true
        var count = 1
        if (!neighbors.containsKey(index)) return count
        for (neighbor in neighbors[index]!!) {
            if (!visited[neighbor]) count += DFS_Core(neighbor, neighbors, visited)
        }
        return count
    }
}