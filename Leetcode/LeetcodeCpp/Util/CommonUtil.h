//
// Created by SpreadZhao on 2024/8/4.
//

#ifndef LEETCODECPP_COMMONUTIL_H
#define LEETCODECPP_COMMONUTIL_H

#include "../common.h"

#define REPEAT(X) for (int i = 0; i < (X); i++)

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    explicit TreeNode(const int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(const int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    explicit ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

class CommonUtil {
public:
    class DFSResponse {
    public:
        int islandNum = 0;
    };
    static bool isEven(int n);
    static DFSResponse dfs(int n, const vector<vector<int>>& edges);
    static TreeNode *buildTreeByDepth(const int nodes[], int size);
    static void traverseTreeByDepth(const TreeNode *root);
    static void traverseTreeInOrder(const TreeNode *root);
    static void printMetric(const int *metric, size_t line, size_t column);
    static void printVectorString(const vector<string> &vec);
    static void printMetric(const vector<vector<int>>& metric);
    static void printLinkedList(ListNode *head);
    static void printVectorInt(const vector<int> &vec);
    static ListNode *newList(const vector<int> &nums);
};



#endif //LEETCODECPP_COMMONUTIL_H
