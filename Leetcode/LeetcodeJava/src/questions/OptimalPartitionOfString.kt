package questions

/**
 * Link: [Optimal Partition of String](https://leetcode.com/problems/optimal-partition-of-string/)
 */
class OptimalPartitionOfString {

//    inner class TrieNode{
//        private val children: HashMap<Char, TrieNode> = HashMap()
//        private var isTerminal = false
//        fun addNode(ch: Char){
//            val curr = this
//            while(!curr.children.containsKey(ch)){
//
//            }
//        }
//    }

    // TLE ...
    fun partitionString(s: String): Int {
        val sub = ArrayList<HashMap<Char, Int>>()
        sub.add(HashMap())
        for (ch in s) {
            if (!sub.last().containsKey(ch)) {
                sub.last()[ch] = 0
                continue
            }
            sub.add(HashMap())
            sub.last()[ch] = 0
        }
        return sub.size
    }

    fun partitionString4(s: String): Int {
        val subs = HashSet<Char>()
        var count = 1
        for (ch in s) {
            if (subs.contains(ch)) {
                count++
                subs.clear()
            }
            subs.add(ch)
        }
        return count
    }

    fun partitionString2(s: String): Int {
        val lastSeen = IntArray(26)
        lastSeen.fill(-1)
        var count = 1;
        var substringStart = 0
        for (i in s.indices) {
            if (lastSeen[s[i] - 'a'] >= substringStart) {
                count++
                substringStart = i
            }
            lastSeen[s[i] - 'a'] = i
        }
        return count
    }

    fun partitionString3(s: String): Int {
        var count = 1;
        var merge = 0
        for (ch in s) {
            val index = ch - 'a'
            if ((merge and (1 shl index)) != 0) {
                count++
                merge = 0
            }
            merge = merge or (1 shl index)
        }
        return count
    }

    fun partitionString5(s: String): Int {
        var count = 1
        val merge = BooleanArray(26)
        merge.fill(false)
        for (ch in s) {
            val index = ch - 'a'
            if (merge[index]) {
                count++
                merge.fill(false)
            }
            merge[index] = true
        }
        return count
    }

    private fun listContain(sub: List<Map<Char, Int>>, ch: Char): Int {
        for (i in sub.indices) {
            if (sub[i].containsKey(ch)) return i
        }
        return -1
    }
}