//
// Created by SpreadZhao on 2024/8/4.
//

#ifndef LEETCODECPP_COMMONUTIL_H
#define LEETCODECPP_COMMONUTIL_H

#include <iterator>
#include <vector>

#include "../common.h"

#define REPEAT(X) for (int i = 0; i < (X); i++)

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    explicit TreeNode(const int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(const int x, TreeNode *left, TreeNode *right)
        : val(x), left(left), right(right) {}
};

struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    explicit ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

class MaxHeap {
   public:
    MaxHeap(const int size) : size_(1), max_size_(size + 1), arr_(max_size_) {}
    MaxHeap(const vector<int> &nums) : size_(nums.size() + 1), max_size_(size_) {
        init(nums);
    }

    int size_;
    int max_size_;
    vector<int> arr_;
    void init(const vector<int> &);
    // parent index
    int parent(int index);
    // left child index
    int leftChild(int index);
    // right child index
    int rightChild(int index);
    // swap two nodes
    void swap(int index1, int index2);
    // valid index
    bool valid(int index);
    // insert an number into max heap
    void insert(int num);
    // remove top
    void pop();
    // get top
    int top();
    // update priority of node
    void move_up(int index);
    void move_down(int index);
    // find max index of this and it's children
    int max_of_three(int parent);
};

class CommonUtil {
   public:
    class DFSResponse {
       public:
        int islandNum = 0;
        // number of nodes in each island
        vector<long long> islandSizes;
    };
    static bool isEven(int n);
    static DFSResponse dfs(int n, const vector<vector<int>> &edges);
    static TreeNode *buildTreeByDepth(const int nodes[], int size);
    static void traverseTreeByDepth(const TreeNode *root);
    static void traverseTreeInOrder(const TreeNode *root);
    static void printMetric(const int *metric, size_t line, size_t column);
    static void printVectorString(const vector<string> &vec);
    static void printMetric(const vector<vector<int>> &metric);
    static void printLinkedList(ListNode *head);
    static void printVectorInt(const vector<int> &vec);
    static ListNode *newList(const vector<int> &nums);
    static long long quickPairSum(const vector<long long> &nums);
};

#endif  // LEETCODECPP_COMMONUTIL_H
