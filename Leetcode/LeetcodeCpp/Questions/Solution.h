//
// Created by SpreadZhao on 2024/7/21.
//

#ifndef LEETCODECPP_SOLUTION_H
#define LEETCODECPP_SOLUTION_H
#include "../common.h"
#include "../Util/CommonUtil.h"

class Solution {
public:
    static string addBinary(string a, string b);
    // https://leetcode.cn/problems/accounts-merge/description/
    static vector<vector<string>> accountsMerge(vector<vector<string>>& accounts);
    // https://leetcode.cn/problems/boats-to-save-people/description/
    static int numRescueBoats(vector<int>& people, int limit);
    // https://leetcode.cn/problems/can-place-flowers/description/
    static bool canPlaceFlowers(vector<int> &flowerbed, int n);
    static bool canPlaceFlowers2(vector<int> &flowerbed, int n);
    // https://leetcode.cn/problems/number-of-operations-to-make-network-connected/description/
    static int makeConnected(int n, vector<vector<int>> &connections);
    // https://leetcode.cn/problems/container-with-most-water/description/
    static int maxArea(vector<int> &height);
    // https://leetcode.cn/problems/find-the-difference-of-two-arrays/
    static vector<vector<int>> findDifference(vector<int> &nums1, vector<int> &nums2);
    static vector<vector<int>> findDifference2(vector<int> &nums1, vector<int> &nums2);
    // https://leetcode.cn/problems/invert-binary-tree/description/
    static TreeNode *invertTree(TreeNode *root);
    // https://leetcode.cn/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/description/
    static int numWays(vector<string> &words, string target);
    // https://leetcode.cn/problems/kids-with-the-greatest-number-of-candies/description/
    static vector<bool> kidsWithCandies(vector<int> &candies, int extraCandies);
    // https://leetcode.cn/problems/longest-increasing-subsequence/description/
    static int lengthOfLIS(vector<int> &nums);
    // https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
    static vector<string> letterCombinations(string digits);
    // https://leetcode.cn/problems/binary-tree-level-order-traversal/description/
    static vector<vector<int>> levelOrder(TreeNode *root);
    // https://leetcode.cn/problems/intersection-of-two-linked-lists/
    static ListNode *getIntersectionNode(ListNode *headA, ListNode *headB);
    static ListNode *getIntersectionNode2(ListNode *headA, ListNode *headB);
    // https://leetcode.cn/problems/longest-common-prefix/description/
    static string longestCommonPrefix(vector<string> &strs);
    // https://leetcode.cn/problems/longest-palindromic-subsequence/description/
    static int longestPalindromeSubseq(string s);
    // https://leetcode.cn/problems/longest-palindromic-substring/description/
    static string longestPalindromicSubstring(string s);
    // https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/
    static int lengthOfLongestSubstring(string s);
    static int lengthOfLongestSubstring2(string s);
    // https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/description/
    static TreeNode *lowestCommonAncestor(TreeNode *root, TreeNode *p, TreeNode *q);
    static TreeNode *lowestCommonAncestor2(TreeNode *root, TreeNode *p, TreeNode *q);
    // https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/
    static int maxDepth(TreeNode *root);
    static int maxDepth2(TreeNode *root);
    // https://leetcode.cn/problems/minimize-maximum-of-array/description/
    static int minimizeArrayValueError(vector<int> &nums);
    static int minimizeArrayValue(vector<int> &nums);
    // https://leetcode.cn/problems/minimum-score-of-a-path-between-two-cities/description/
    static int minScore(int n, vector<vector<int>> &roads);
    // https://leetcode.cn/problems/optimal-partition-of-string/description/
    static int partitionString(string s);
    static int partitionString2(string s);
};

#endif //LEETCODECPP_SOLUTION_H
