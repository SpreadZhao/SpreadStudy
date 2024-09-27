package questions

import model.TreeNode

/**
 * https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/description/
 */

class LowestCommonAncestor {
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (p == null || q == null) return null
        return traverseTree(root, p, q)
    }

    private fun traverseTree(curr: TreeNode?, p: TreeNode, q: TreeNode): TreeNode? {
        if (curr == null) return null
        var ancestor = curr
        if (canSearchPAndQ(curr.left, p, q, booleanArrayOf(false, false))) {
            ancestor = traverseTree(curr.left, p, q)
        } else if (canSearchPAndQ(curr.right, p, q, booleanArrayOf(false, false))) {
            ancestor = traverseTree(curr.right, p, q)
        }
        return ancestor
    }

    // 以当前结点为根节点，能搜索到p和q
    private fun canSearchPAndQ(curr: TreeNode?, p: TreeNode, q: TreeNode, search: BooleanArray): Boolean {
        if (curr == null) return false
        if (curr.`val` == p.`val`) search[0] = true
        if (curr.`val` == q.`val`) search[1] = true
        if (curr.left != null)  canSearchPAndQ(curr.left, p, q, search)
        if (curr.right != null)  canSearchPAndQ(curr.right, p, q, search)
        return search[0] && search[1]
    }

    fun lowestCommonAncestor2(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || p == null || q == null) return null
        var ancestor: TreeNode = root
        while (true) {
            ancestor = if (p.`val` < ancestor.`val` && q.`val` < ancestor.`val`) ancestor.left
            else if (p.`val` > ancestor.`val` && q.`val` > ancestor.`val`) ancestor.right
            else break
        }
        return ancestor
    }
}