#include <iostream>
#include "Questions/Solution.h"

Solution solution;

void AddBinary() {
    string a = "101";
    string b = "10101";
    string str = Solution::addBinary(a, b);
    cout << "str: " << str;
}

void AccountsMerge() {
    vector<vector<string> > vec = {
        {"John", "johnsmith@mail.com", "john_newyork@mail.com"},
        {"John", "johnsmith@mail.com", "john00@mail.com"},
        {"Mary", "mary@mail.com"},
        {"John", "johnnybravo@mail.com"}
    };
    vector<vector<string> > res = Solution::accountsMerge(vec);
    for (const auto &row: res) {
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
    vector<int> people = {3, 5, 3, 4};
    int limit = 5;
    cout << Solution::numRescueBoats(people, limit);
}

void CanPlaceFlowers() {
    vector<int> flowerbed = {1, 0, 0, 0, 1};
    int n = 1;
    cout << Solution::canPlaceFlowers2(flowerbed, n);
}

void MakeConnected() {
    int n = 4;
    vector<vector<int> > connections = {
        {0, 1},
        {0, 2},
        {1, 2}
    };
    cout << Solution::makeConnected(n, connections);
}

void MaxArea() {
    vector<int> height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
    cout << Solution::maxArea(height);
}

void FindDifference() {
    vector<int> nums1 = {1, 2, 3};
    vector<int> nums2 = {2, 4, 6};
    auto res = Solution::findDifference(nums1, nums2);
    for (auto num: res[0]) {
        cout << num << " ";
    }
    cout << endl;
    for (auto num: res[1]) {
        cout << num << " ";
    }
}

void InvertTree() {
    int nodes[] = {4, 2, 7, 1, 3, 6, 9};
    TreeNode *root = CommonUtil::buildTreeByDepth(nodes, 7);
    cout << "my tree: ";
    CommonUtil::traverseTreeByDepth(root);
    cout << endl;
    Solution::invertTree(root);
    cout << "after inverted: ";
    CommonUtil::traverseTreeByDepth(root);
}

void NumWays() {
    vector<string> words = {
        "acca", "bbbb", "caca"
    };
    string target = "aba";
    cout << Solution::numWays(words, target);
}

void LengthOfLIS() {
    vector<int> nums = {10, 9, 2, 5, 3, 7, 101, 18};
    vector<int> nums2 = {0, 1, 0, 3, 2, 3};
    vector<int> nums3 = {1, 3, 6, 7, 9, 4, 10, 5, 6};
    cout << Solution::lengthOfLIS(nums3);
}

void LetterCombination() {
    string letters = "569";
    CommonUtil::printVectorString(Solution::letterCombinations(letters));
}

void LevelOrder() {
    int nodes[] = {3, 9, 20, -1, -1, 15, 7};
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
    int nodes[] = {6, 2, 8, 0, 4, 7, 9, -1, -1, 3, 5};
    TreeNode *root = CommonUtil::buildTreeByDepth(nodes, sizeof(nodes) / sizeof(nodes[0]));
    auto *p = new TreeNode(2);
    auto *q = new TreeNode(4);
    int nodes2[] = {2, 1};
    TreeNode *root2 = CommonUtil::buildTreeByDepth(nodes2, sizeof(nodes2) / sizeof(nodes2[0]));
    auto *p2 = new TreeNode(1);
    auto *q2 = new TreeNode(2);
    int nodes3[] = {5, 3, 6, 2, 4, -1, -1, 1};
    TreeNode *root3 = CommonUtil::buildTreeByDepth(nodes3, sizeof(nodes3) / sizeof(nodes3[0]));
    auto *p3 = new TreeNode(1);
    auto *q3 = new TreeNode(3);
    cout << Solution::lowestCommonAncestor(root, p, q)->val << endl;
    cout << Solution::lowestCommonAncestor(root2, p2, q2)->val << endl;
    cout << Solution::lowestCommonAncestor(root3, p3, q3)->val << endl;
}

void MaxDepth() {
    int nodes[] = {3, 9, 20, -1, -1, 15, 7};
    TreeNode *root = CommonUtil::buildTreeByDepth(nodes, sizeof(nodes) / sizeof(nodes[0]));
    cout << Solution::maxDepth2(root) << endl;
}

void MinimizeMaximum() {
    vector<int> nums = {3, 7, 1, 6};
    cout << Solution::minimizeArrayValue(nums);
}

void MinScore() {
    vector<vector<int>> roads = {
        {1, 2, 9},
        {2, 3, 6},
        {2, 4, 5},
        {1, 4, 7}
    };
    int n = 4;
    cout << Solution::minScore(n, roads);
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
    MinScore();
    return 0;
}
