package questions;

import java.util.*;

/**
 * Link: <a href="https://leetcode.com/problems/3sum/description/">Sum</a>
 * Idea: HashMap
 */
public class Sum {
    // Time Limit Exceeded
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> l = new ArrayList<>();
                        boolean duplicate = false;
                        for (List<Integer> candidate : res) {
                            if (candidate.contains(nums[i]) && candidate.contains(nums[j]) && candidate.contains(nums[k])) {
                                if (match(candidate, nums[i], nums[j], nums[k])) {
                                    duplicate = true;
                                    break;
                                }
                            }
                        }
                        if (!duplicate) {
                            l.add(nums[i]);
                            l.add(nums[j]);
                            l.add(nums[k]);
                            res.add(l);
                        }
                    }
                }
            }
        }
        return res;
    }


    public List<List<Integer>> threeSum2(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();
        if (nums.length == 0) return new ArrayList<>();
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) set.add(Arrays.asList(nums[i], nums[j++], nums[k--]));
                if (sum < 0) j++;
                if (sum > 0) k--;

            }
        }
        return new ArrayList<>(set);
    }

    // 1 2 4,   a = 2, b = 1, c = 8
    public boolean match(List<Integer> l, int a, int b, int c) {
        boolean aMatched = false;
        boolean bMatched = false;
        boolean cMatched = false;
        for (int i = 0; i < 3; i++) {
            if (!aMatched) {
                if (l.get(i) == a) {
                    aMatched = true;
                    continue;
                }
            }
            if (!bMatched) {
                if (l.get(i) == b) {
                    bMatched = true;
                    continue;
                }
            }
            if (!cMatched) {
                if (l.get(i) == c) {
                    cMatched = true;
                }
            }
        }
        return aMatched & bMatched & cMatched;
    }
}
