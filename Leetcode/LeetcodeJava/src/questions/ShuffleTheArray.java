package questions;

/**
 * Link: <a href="https://leetcode.com/problems/shuffle-the-array/">Shuffle the Array</a>
 */
public class ShuffleTheArray {
    public int[] shuffle(int[] nums, int n) {
        int[] res = new int[2 * n];
        int xi = 0, yi = n;
        for (int i = 0; i < 2 * n; i++) {
            if (i % 2 == 0) {
                res[i] = nums[xi++];
            } else {
                res[i] = nums[yi++];
            }
        }
        return res;
    }
}
