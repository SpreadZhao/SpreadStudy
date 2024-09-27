package questions;

/**
 * Link: <a href="https://leetcode.com/problems/two-sum/">Two Sum</a>
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[0];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (target == nums[i] + nums[j]) {
                    res = new int[]{i, j};
                }
            }
        }
        return res;
    }
}
