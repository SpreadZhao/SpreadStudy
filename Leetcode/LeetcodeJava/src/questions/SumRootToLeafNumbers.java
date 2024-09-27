package questions;

import model.TreeNode;

public class SumRootToLeafNumbers {
    public int sumNumbers(TreeNode root) {
//        int sum = root.val;
//        if(root.left == null && root.right == null) return sum;
//        if(root.right == null){
//            int left = sumNumbers(root.left);
//            return sum * (int)Math.pow(10, getLen(left)) + left;
//        }
//        if(root.left == null){
//            int right = sumNumbers(root.right);
//            return sum * (int)Math.pow(10, getLen(right)) + right;
//        }
//        int left = sumNumbers(root.left);
//        int right = sumNumbers(root.right);
//        return sum * (int)Math.pow(10, getLen(left)) + left + sum * (int)Math.pow(10, getLen(right)) + right;


//        int leftSum = 0, rightSum = 0;
//        if(root.left == null && root.right == null) return root.val;
//        if(root.left != null) leftSum += sumNumbers(root.left);
//        if(root.right != null) rightSum += sumNumbers(root.right);
//        return leftSum + rightSum + root.val;


        return recursive(root, 0);
    }

    public int recursive(TreeNode root, int currSum) {
        currSum = currSum * 10 + root.val;
        if (root.left == null && root.right == null) return currSum;
        int left = 0, right = 0;
        if (root.left != null) left = recursive(root.left, currSum);
        if (root.right != null) right = recursive(root.right, currSum);
        return left + right;
    }


    public int getLen(int n) {
        int len = 0;
        while (n > 0) { // 9
            n /= 10;
            len++;
        }
        return len;
    }
}
