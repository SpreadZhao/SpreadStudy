#include <iostream>
#include <vector>

#include "Questions/Solution.h"
#include "Util/CommonUtil.h"

Solution solution;

void AddBinary() {
    string a = "101";
    string b = "10101";
    string str = Solution::addBinary(a, b);
    cout << "str: " << str;
}

void AccountsMerge() {
    vector<vector<string>> vec = {
        { "John", "johnsmith@mail.com", "john_newyork@mail.com" },
        { "John", "johnsmith@mail.com", "john00@mail.com" },
        { "Mary", "mary@mail.com" },
        { "John", "johnnybravo@mail.com" }
    };
    vector<vector<string>> res = Solution::accountsMerge(vec);
    for (const auto &row : res) {
        for (int i = 0; i < row.size(); i++) {
            if (i == 0) {
                cout << row[i] << ": ";
            } else {
                cout << row[i] << ", ";
            }
        }
        cout << endl;
    }
}

void BoatsToSavePeople() {
    vector<int> people = { 3, 5, 3, 4 };
    int limit = 5;
    cout << Solution::numRescueBoats(people, limit);
}

void CanPlaceFlowers() {
    vector<int> flowerbed = { 1, 0, 0, 0, 1 };
    int n = 1;
    cout << Solution::canPlaceFlowers2(flowerbed, n);
}

void MakeConnected() {
    int n = 4;
    vector<vector<int>> connections = { { 0, 1 }, { 0, 2 }, { 1, 2 } };
    cout << Solution::makeConnected(n, connections);
}

void MaxArea() {
    vector<int> height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
    cout << Solution::maxArea(height);
}

void FindDifference() {
    vector<int> nums1 = { 1, 2, 3 };
    vector<int> nums2 = { 2, 4, 6 };
    auto res = Solution::findDifference(nums1, nums2);
    for (auto num : res[0]) {
        cout << num << " ";
    }
    cout << endl;
    for (auto num : res[1]) {
        cout << num << " ";
    }
}

void InvertTree() {
    int nodes[] = { 4, 2, 7, 1, 3, 6, 9 };
    TreeNode *root = CommonUtil::buildTreeByDepth(nodes, 7);
    cout << "my tree: ";
    CommonUtil::traverseTreeByDepth(root);
    cout << endl;
    Solution::invertTree(root);
    cout << "after inverted: ";
    CommonUtil::traverseTreeByDepth(root);
}

void NumWays() {
    vector<string> words = { "acca", "bbbb", "caca" };
    string target = "aba";
    cout << Solution::numWays(words, target);
}

void LengthOfLIS() {
    vector<int> nums = { 10, 9, 2, 5, 3, 7, 101, 18 };
    vector<int> nums2 = { 0, 1, 0, 3, 2, 3 };
    vector<int> nums3 = { 1, 3, 6, 7, 9, 4, 10, 5, 6 };
    cout << Solution::lengthOfLIS2(nums3);
}

void LetterCombination() {
    string letters = "569";
    CommonUtil::printVectorString(Solution::letterCombinations(letters));
}

void LevelOrder() {
    int nodes[] = { 3, 9, 20, -1, -1, 15, 7 };
    TreeNode *root = CommonUtil::buildTreeByDepth(nodes, 7);
    cout << "my tree: ";
    CommonUtil::traverseTreeByDepth(root);
    cout << endl;
    cout << "by level: ";
    Solution::levelOrder(root);
}

void LongestPalindromSubseq() {
    string s = "bbbab";
    cout << Solution::longestPalindromeSubseq(s);
}

void LongestPalindromicSubstring() {
    string s = "aaca";
    cout << Solution::longestPalindromicSubstring(s);
}

void LengthOfLongestSubstring() {
    string s = "abcabcbb";
    string s2 = "bbbbb";
    string s3 = "pwwkew";
    string s4 = "aab";
    string s5 = "dvdf";
    string s6 = "tmmzuxt";
    cout << Solution::lengthOfLongestSubstring(s) << endl;
    cout << Solution::lengthOfLongestSubstring(s2) << endl;
    cout << Solution::lengthOfLongestSubstring(s3) << endl;
}

void LowestCommonAncestor() {
    int nodes[] = { 6, 2, 8, 0, 4, 7, 9, -1, -1, 3, 5 };
    TreeNode *root =
        CommonUtil::buildTreeByDepth(nodes, sizeof(nodes) / sizeof(nodes[0]));
    auto *p = new TreeNode(2);
    auto *q = new TreeNode(4);
    int nodes2[] = { 2, 1 };
    TreeNode *root2 = CommonUtil::buildTreeByDepth(
        nodes2, sizeof(nodes2) / sizeof(nodes2[0]));
    auto *p2 = new TreeNode(1);
    auto *q2 = new TreeNode(2);
    int nodes3[] = { 5, 3, 6, 2, 4, -1, -1, 1 };
    TreeNode *root3 = CommonUtil::buildTreeByDepth(
        nodes3, sizeof(nodes3) / sizeof(nodes3[0]));
    auto *p3 = new TreeNode(1);
    auto *q3 = new TreeNode(3);
    cout << Solution::lowestCommonAncestor(root, p, q)->val << endl;
    cout << Solution::lowestCommonAncestor(root2, p2, q2)->val << endl;
    cout << Solution::lowestCommonAncestor(root3, p3, q3)->val << endl;
}

void MaxDepth() {
    int nodes[] = { 3, 9, 20, -1, -1, 15, 7 };
    TreeNode *root =
        CommonUtil::buildTreeByDepth(nodes, sizeof(nodes) / sizeof(nodes[0]));
    cout << Solution::maxDepth2(root) << endl;
}

void MinimizeMaximum() {
    vector<int> nums = { 3, 7, 1, 6 };
    cout << Solution::minimizeArrayValue(nums);
}

void MinScore() {
    vector<vector<int>> roads = {
        { 1, 2, 9 }, { 2, 3, 6 }, { 2, 4, 5 }, { 1, 4, 7 }
    };
    int n = 4;
    cout << Solution::minScore(n, roads);
}

void PartitionString() {
    string s = "abacaba";
    string s2 = "ssssss";
    cout << Solution::partitionString2(s2);
}

void Permutation() {
    vector<int> nums = { 1, 2, 3 };
    vector<int> nums2 = { 0, 1 };
    auto permutation = Solution::permute(nums);
    CommonUtil::printMetric(permutation);
}

void Reverse() {
    int num = 123;
    cout << Solution::reverse(num) << endl;
}

void ReverseList() {
    vector<int> nums = { 1, 2, 3, 4, 5 };
    ListNode *head = CommonUtil::newList(nums);
    cout << "before: ";
    CommonUtil::printLinkedList(head);
    cout << "after: ";
    CommonUtil::printLinkedList(Solution::reverseList(head));
}

void ReverseBetween() {
    vector<int> nums = { 1, 2, 3, 4, 5 };
    vector<int> nums2 = { 3, 5 };
    ListNode *head = CommonUtil::newList(nums2);
    cout << "before: ";
    CommonUtil::printLinkedList(head);
    cout << "after: ";
    CommonUtil::printLinkedList(Solution::reverseBetween(head, 1, 2));
}

void SearchInsert() {
    vector<int> nums = { 1, 3, 5, 6 };
    cout << Solution::searchInsert(nums, 7);
}

void SimplifyPath() {
    string path = "/../";
    cout << Solution::simplifyPath2(path) << endl;
}

void SingleNonDuplicate() {
    vector<int> nums = { 1 };
    cout << Solution::singleNonDuplicate(nums) << endl;
}

void SortedListToBLT() {
    auto *list = CommonUtil::newList(vector<int>{ -10, -3, 0, 5, 9 });
    CommonUtil::traverseTreeInOrder(Solution::sortedListToBST(list));
}

void SuccessfulPairs() {
    auto spells = vector<int>{ 3, 1, 2 };
    auto potions = vector<int>{ 8, 5, 8 };
    auto spells2 = vector<int>{ 5, 1, 3 };
    auto potions2 = vector<int>{ 1, 2, 3, 4, 5 };
    auto spells3 = vector<int>{ 15, 8, 19 };
    auto potions3 = vector<int>{ 38, 36, 23 };
    auto spells4 = vector<int>{ 38, 11 };
    auto potions4 = vector<int>{ 3, 20 };
    auto spells5 = vector<int>{ 9, 39 };
    auto potions5 = vector<int>{ 35, 40, 22, 37, 29, 22 };
    long long success = 16;
    long long success2 = 7;
    long long success3 = 328;
    long long success4 = 1065;
    long long success5 = 320;
    CommonUtil::printVectorInt(
        Solution::successfulPairs4(spells5, potions5, success5));
}

void SpiralMatrix() {
    vector<vector<int>> matrix = { { 1, 2, 3, 4 },
                                   { 5, 6, 7, 8 },
                                   { 9, 10, 11, 12 } };
    CommonUtil::printVectorInt(Solution::spiralOrder(matrix));
}

void ThreeSum() {
    vector<int> nums = { -1, 0, 1, 2, -1, -4 };
    CommonUtil::printMetric(Solution::threeSum(nums));
}

void SumNumbers() {
    int nums[] = { 4, 9, 0, 5, 1 };
    TreeNode *root = CommonUtil::buildTreeByDepth(nums, 5);
    cout << Solution::sumNumbers(root) << endl;
}

void IsSymmetric() {
    int nums[] = { 1, 2, 2, 3, 4, 4, 3 };
    int nums2[] = { 1, 2, 2, -1, 3, -1, 3 };
    TreeNode *root = CommonUtil::buildTreeByDepth(nums2, 7);
    cout << Solution::isSymmetric(root) << endl;
}

void ImplementTrie() {
    auto *trie = new Solution::Trie();
    trie->insert("apple");
    cout << trie->search("apple") << endl;
}

void CountPairs() {
    int n = 3;
    vector<vector<int>> edges = { { 0, 1 }, { 0, 2 }, { 1, 2 } };
    cout << Solution::countPairs(n, edges);
}

void ValidParentheses() {
    string s = "([)";
    cout << Solution::isValid(s);
}

void SearchWord() {
    Solution::WordDictionary *dictionary = new Solution::WordDictionary();
    // dictionary->addWord("bad");
    // dictionary->addWord("dad");
    // dictionary->addWord("mad");
    // cout << dictionary->search("pad");
    // cout << dictionary->search("bad");
    // cout << dictionary->search(".ad");
    // cout << dictionary->search("b..");
    dictionary->addWord("a");
    dictionary->addWord("a");
    cout << dictionary->search("a");
    cout << dictionary->search("aa");
}

void Zigzag() {
    string s = "AB";
    int numRows = 1;
    cout << Solution::convert(s, numRows);
}

void LRUCache() {
    // Solution::LRUCache *cache = new Solution::LRUCache(2);
    Solution::LRUCache2 cache = Solution::LRUCache2(2);
    cache.put(1, 1);
    cache.put(2, 2);
    cout << cache.get(1);
    cache.put(3, 3);
    cout << cache.get(2);
}

void MergeTwoSortedLists() {
    ListNode *list1 = CommonUtil::newList(vector<int>{ 1, 2, 4 });
    ListNode *list2 = CommonUtil::newList(vector<int>{ 1, 3, 4 });
    Solution::mergeTwoLists(list1, list2);
}

void InsertionSortList() {
    ListNode *list = CommonUtil::newList(vector<int>{ 4, 2, 1, 3 });
    ListNode *sorted = Solution::insertionSortList(list);
    CommonUtil::printLinkedList(sorted);
}

void SortList() {
    ListNode *list = CommonUtil::newList(vector<int>{ -1, 5, 3, 4, 0 });
    CommonUtil::printLinkedList(Solution::sortList2(list));
}

void TestMaxHeap() {
    MaxHeap heap(5);
    heap.insert(5);
    heap.insert(8);
    heap.insert(1);
    heap.insert(4);
    heap.insert(3);
    for (int i = 0; i < 5; i++) {
        int top = heap.top();
        cout << "top: " << top << endl;
        heap.pop();
    }
}

void KthLargest() {
    vector<int> nums = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
    vector<int> nums2 = { 3, 2, 1, 5, 6, 4 };
    int kl = Solution::findKthLargest2(nums2, 2);
    cout << kl;
}

void MaximumSubarray() {
    vector<int> nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
    cout << Solution::maxSubArray(nums);
}

void SearchInSortedArray() {
    vector<int> nums = { 4, 5, 6, 7, 8, 1, 2, 3 };
    cout << Solution::search(nums, 2);
}

void NumberOfIslands() {
    vector<vector<char>> grid = {
        { '1', '1', '1', '1', '0' },
        { '1', '1', '0', '1', '0' },
        { '1', '1', '0', '0', '0' },
        { '0', '0', '0', '0', '0' }
    };
    vector<vector<char>> grid2 = {
        { '1', '1', '1' },
        { '0', '1', '0' },
        { '1', '1', '1' }
    };
    cout << Solution::numIslands(grid2);
}

int main() {
    // AddBinary();
    //    AccountsMerge();
    //    BoatsToSavePeople();
    // CanPlaceFlowers();
    // MakeConnected();
    // MaxArea();
    // FindDifference();
    // InvertTree();
    // NumWays();
    // LengthOfLIS();
    // LetterCombination();
    // LevelOrder();
    // LongestPalindromSubseq();
    // LongestPalindromicSubstring();
    // LengthOfLongestSubstring();
    // LowestCommonAncestor();
    // MaxDepth();
    // MinimizeMaximum();
    // MinScore();
    // PartitionString();
    // Permutation();
    // Reverse();
    // ReverseList();
    // ReverseBetween();
    // SearchInsert();
    // SimplifyPath();
    // SingleNonDuplicate();
    // SortedListToBLT();
    // SuccessfulPairs();
    // SpiralMatrix();
    // ThreeSum();
    // SumNumbers();
    // IsSymmetric();
    // ImplementTrie();
    // CountPairs();
    // ValidParentheses();
    // SearchWord();
    // Zigzag();
    // LRUCache();
    // MergeTwoSortedLists();
    // InsertionSortList();
    // SortList();
    // TestMaxHeap();
    // KthLargest();
    // MaximumSubarray();
    // SearchInSortedArray();
    NumberOfIslands();
    return 0;
}
