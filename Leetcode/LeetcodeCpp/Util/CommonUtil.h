//
// Created by SpreadZhao on 2024/8/4.
//

#ifndef LEETCODECPP_COMMONUTIL_H
#define LEETCODECPP_COMMONUTIL_H

#include "../common.h"

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
    explicit ListNode(int x) : val(x), next(nullptr) {}
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
    static void printMetric(const int *metric, size_t line, size_t column);
    static void printVectorString(const vector<string> &vec);
};



#endif //LEETCODECPP_COMMONUTIL_H
