package questions

import model.TreeNode
import java.util.LinkedList
import java.util.Queue

class LevelOrder {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val ret = ArrayList<List<Int>>()
        if (root == null) return ret
        val queue: Queue<TreeNode> = LinkedList()
        queue.offer(root)
        while (queue.isNotEmpty()) {
            val level = ArrayList<Int>()
            val currentLevelSize = queue.size
            for (i in 1..currentLevelSize) {
                val node = queue.poll()
                level.add(node.`val`)
                if (node.left != null) queue.offer(node.left)
                if (node.right != null) queue.offer(node.right)
            }
            ret.add(level)
        }
        return ret
    }
}