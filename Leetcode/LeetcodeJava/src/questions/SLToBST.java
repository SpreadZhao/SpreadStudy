package questions;

import model.ListNode;
import model.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Link: <a href="https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree">Convert Sorted List to Binary Search Tree</a>
 * <p>
 * Set The middle element of the array as root.
 * <p>
 * Recursively do the same for the left half and right half.
 * <p>
 * Get the middle of the left half and make it the left child of the root created in step 1.
 * <p>
 * Get the middle of the right half and make it the right child of the root created in step 1.
 * <p>
 * Print the preorder of the tree.
 */
public class SLToBST {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        List<Integer> nums = new ArrayList<>();
        while (head != null) {
            nums.add(head.val);
            head = head.next;
        }
        return cutRecursive(nums);
    }

    public TreeNode cutRecursive(List<Integer> nums) {
        int medianIndex = findMedian(nums);
        TreeNode root = new TreeNode(nums.get(medianIndex));
        List<Integer> left = nums.subList(0, medianIndex);
        List<Integer> right = nums.subList(medianIndex + 1, nums.size());
        if (!left.isEmpty()) root.left = cutRecursive(left);
        if (!right.isEmpty()) root.right = cutRecursive(right);
//        if(!left.isEmpty()){
//            root.left = new TreeNode(left.get(findMedian(left)));
//        }
//        if(!right.isEmpty()){
//            root.right = new TreeNode(right.get(findMedian(right)));
//        }

        return root;
    }

    public int findMedian(List<Integer> nums) {
        return nums.size() / 2;
    }
}
