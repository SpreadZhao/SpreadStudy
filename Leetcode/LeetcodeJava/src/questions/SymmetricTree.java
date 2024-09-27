package questions;

import model.TreeNode;

/**
 * Link: <a href="https://leetcode.com/problems/symmetric-tree/">Symmetric Tree</a>
 * <p>
 * Guess: After inversion, the tree is the same as the former one.
 * <p>
 * <a href="https://leetcode.com/problems/symmetric-tree/solutions/3290132/python-java-c-video-explanation/">Solution</a>
 */
public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        //TreeNode newRoot = new TreeNode(root.val);
        //invertTree(root, newRoot);
        TreeNode save = saveTree(root);
        invertTree(root);
        return preOrderCompare(root, save);
    }

    public TreeNode saveTree(TreeNode root) {
        TreeNode newRoot = new TreeNode(root.val);
        if (root.left != null) newRoot.left = saveTree(root.left);
        if (root.right != null) newRoot.right = saveTree(root.right);
        return newRoot;
    }

    public void invertTree(TreeNode root) {
        if (root == null) return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
    }

    public boolean preOrderCompare(TreeNode a, TreeNode b) {
//        boolean flag;
//        if(a != null && b != null) flag = a.val == b.val;
//        else if(a != null) flag = false;
//        else if(b != null) flag = false;
//        else flag = true;
//        //boolean flag = a.val == b.val;
//        if(a.left != null && b.left != null) flag &= preOrderCompare(a.left, b.left); else flag = false;
//        if(a.right != null && b.right != null) flag &= preOrderCompare(a.right, b.right); else flag = false;
//        return flag;

//        boolean flag = true;
//
//        if(a.val != b.val) flag = false;
//        if(a.left != null && b.left != null) flag &= preOrderCompare(a.left, b.left);
////        else if(a.left != null) flag = false;
////        else if(b.left != null) flag = false;
////        else flag = true;
//        else flag = false;
//        return flag;

//        boolean flag = true;
//        if(a == null && b == null) {}
//        else if(a != null && b == null) flag = false;
//        else if(a == null && b != null) flag = false;
//        else if(a.val != b.val) flag = false;
//
//        flag &= preOrderCompare(a.left, b.left);
//        flag &= preOrderCompare(a.right, b.right);
//        return flag;

        boolean flag = true;
        if (a.val != b.val) flag = false;
        if (a.left != null && b.left != null) flag &= preOrderCompare(a.left, b.left);
        else if (a.left == null && b.left == null) {
        } else flag = false;
        if (a.right != null && b.right != null) flag &= preOrderCompare(a.right, b.right);
        else if (a.right == null && b.right == null) {
        } else flag = false;
        return flag;
    }
}
