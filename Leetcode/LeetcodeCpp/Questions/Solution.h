//
// Created by SpreadZhao on 2024/7/21.
//

#ifndef LEETCODECPP_SOLUTION_H
#define LEETCODECPP_SOLUTION_H
#include <map>
#include <string>
#include <type_traits>

#include "../Util/CommonUtil.h"
#include "../common.h"

class Solution {
   public:
    static string addBinary(string a, string b);
    // https://leetcode.cn/problems/accounts-merge/description/
    static vector<vector<string>> accountsMerge(
        vector<vector<string>> &accounts);
    // https://leetcode.cn/problems/boats-to-save-people/description/
    static int numRescueBoats(vector<int> &people, int limit);
    // https://leetcode.cn/problems/can-place-flowers/description/
    static bool canPlaceFlowers(vector<int> &flowerbed, int n);
    static bool canPlaceFlowers2(vector<int> &flowerbed, int n);
    // https://leetcode.cn/problems/number-of-operations-to-make-network-connected/description/
    static int makeConnected(int n, vector<vector<int>> &connections);
    // https://leetcode.cn/problems/container-with-most-water/description/
    static int maxArea(vector<int> &height);
    // https://leetcode.cn/problems/find-the-difference-of-two-arrays/
    static vector<vector<int>> findDifference(vector<int> &nums1,
                                              vector<int> &nums2);
    static vector<vector<int>> findDifference2(vector<int> &nums1,
                                               vector<int> &nums2);
    // https://leetcode.cn/problems/invert-binary-tree/description/
    static TreeNode *invertTree(TreeNode *root);
    // https://leetcode.cn/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/description/
    static int numWays(vector<string> &words, string target);
    // https://leetcode.cn/problems/kids-with-the-greatest-number-of-candies/description/
    static vector<bool> kidsWithCandies(vector<int> &candies, int extraCandies);
    // https://leetcode.cn/problems/longest-increasing-subsequence/description/
    static int lengthOfLIS(vector<int> &nums);
    static int lengthOfLIS2(vector<int> &nums);
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
    static TreeNode *lowestCommonAncestor(TreeNode *root, TreeNode *p,
                                          TreeNode *q);
    static TreeNode *lowestCommonAncestor2(TreeNode *root, TreeNode *p,
                                           TreeNode *q);
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
    // https://leetcode.cn/problems/palindrome-number/description/
    static bool isPalindrome(int x);
    static bool isPalindrome2(int x);
    // https://leetcode.cn/problems/permutations/description/ance
    static vector<vector<int>> permute(vector<int> &nums);
    // https://leetcode.cn/problems/reverse-integer/description/
    static int reverse(int x);
    // https://leetcode.cn/problems/reverse-linked-list/description/
    static ListNode *reverseList(ListNode *head);
    static ListNode *reverseList2(ListNode *head);
    // https://leetcode.cn/problems/reverse-linked-list-ii/description/
    static ListNode *reverseBetween(ListNode *head, int left, int right);
    // https://leetcode.cn/problems/search-insert-position/description/
    static int searchInsert(vector<int> &nums, int target);
    // https://leetcode.cn/problems/simplify-path/description/
    static string simplifyPath(string path);
    static string simplifyPath2(string path);
    // https://leetcode.cn/problems/single-element-in-a-sorted-array/description/
    static int singleNonDuplicate(vector<int> &nums);
    // https://leetcode.cn/problems/convert-sorted-list-to-binary-search-tree/description/
    static TreeNode *sortedListToBST(ListNode *head);
    static TreeNode *sortedListToBST2(ListNode *head);
    // https://leetcode.cn/problems/successful-pairs-of-spells-and-potions/description/
    static vector<int> successfulPairs(vector<int> &spells,
                                       vector<int> &potions, long long success);
    static vector<int> successfulPairs2(vector<int> &spells,
                                        vector<int> &potions,
                                        long long success);
    static vector<int> successfulPairs3(vector<int> &spells,
                                        vector<int> &potions,
                                        long long success);
    // https://leetcode.cn/problems/spiral-matrix/description/
    static vector<int> spiralOrder(vector<vector<int>> &matrix);
    // https://leetcode.cn/problems/3sum/description/
    static vector<vector<int>> threeSum(vector<int> &nums);
    static vector<vector<int>> threeSum2(vector<int> &nums);
    static vector<vector<int>> threeSum3(vector<int> &nums);
    // https://leetcode.cn/problems/two-sum/description/
    static vector<int> twoSum(vector<int> &nums, int target);
    static vector<int> twoSum2(vector<int> &nums, int target);
    // https://leetcode.cn/problems/3sum-closest/description/
    static int threeSumCloset(vector<int> &nums, int target);
    // https://leetcode.cn/problems/sum-root-to-leaf-numbers/description/
    static int sumNumbers(TreeNode *root);
    // https://leetcode.cn/problems/symmetric-tree/description/
    static bool isSymmetric(TreeNode *root);

    // https://leetcode.cn/problems/implement-trie-prefix-tree/description/
    class Trie {
       public:
        Trie();

        void insert(string word);

        bool search(string word);

        bool startsWith(string prefix);

       private:
        class TrieNode {
           public:
            bool is_last = false;
            map<char, TrieNode *> children;
        };

        TrieNode *roots[26]{ nullptr };
    };

    // https://leetcode.cn/problems/count-unreachable-pairs-of-nodes-in-an-undirected-graph/
    static long long countPairs(int n, vector<vector<int>> &edges);

    // https:// leetcode.cn/problems/valid-parentheses/description/
    static bool isValid(string s);

    // https://leetcode.cn/problems/design-add-and-search-words-data-structure/description/
    class WordDictionary {
       public:
        WordDictionary();
        void addWord(string word);
        bool search(string word);

       private:
        class TrieNode {
           public:
            map<char, TrieNode *> children;
            bool is_last = false;
        };
        TrieNode *root;
        bool searchFrom(string word, int startIndex, TrieNode *start);
    };

    // https://leetcode.cn/problems/zigzag-conversion/
    static string convert(string s, int numRow);

};

#endif  // LEETCODECPP_SOLUTION_H
