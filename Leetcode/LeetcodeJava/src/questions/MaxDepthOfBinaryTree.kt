package questions

import model.TreeNode
import java.lang.Integer.max

/**
 * Link: [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)
 */
class MaxDepthOfBinaryTree {
    // Oh no..
    private val len = 0
    private var leftNull = false
    private var rightNull = false
    fun maxDepth(root: TreeNode?): Int {
        //int len = 0;
        if (root == null) return len
        //if() len++;
        if (root.left != null) {
            leftNull = false
            maxDepth(root.left)
        } else leftNull = true
        if (root.right != null) {
            rightNull = false
            maxDepth(root.right)
        } else rightNull = true
        return len
    }

    fun maxDepth2(root: TreeNode?): Int {
        if (root == null) return 0
        if (root.left == null && root.right == null) return 1
        var max = 1
        max += max(maxDepth2(root.left), maxDepth2(root.right))
        return max
    }
}