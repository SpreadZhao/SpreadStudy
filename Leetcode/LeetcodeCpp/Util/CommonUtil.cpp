//
// Created by SpreadZhao on 2024/8/4.
//
#include "CommonUtil.h"
#include <ios>

bool CommonUtil::isEven(int n) { return n % 2 == 0; }

map<int, vector<int> > buildMapForEdges(const vector<vector<int> > &edges) {
    map<int, vector<int> > m;
    for (auto &edge: edges) {
        int a = edge[0];
        int b = edge[1];
        if (!m.count(a)) {
            m[a] = vector<int>{};
        }
        if (!m.count(b)) {
            m[b] = vector<int>{};
        }
        m[a].emplace_back(b);
        m[b].emplace_back(a);
    }
    return m;
}

void dfsCore(int start, const map<int, vector<int> > &edgesMap, vector<bool> &visited, long long &currIslandNodeCount) {
    if (visited[start]) {
        return;
    }
    visited[start] = true;
    currIslandNodeCount++;
    if (!edgesMap.count(start)) {
        return;
    }
    for (const auto neighbor: edgesMap.at(start)) {
        if (!visited[neighbor]) {
            dfsCore(neighbor, edgesMap, visited, currIslandNodeCount);
        }
    }
}

CommonUtil::DFSResponse CommonUtil::dfs(const int n, const vector<vector<int> > &edges) {
    DFSResponse response;
    vector<bool> visited(n, false);
    const auto map = buildMapForEdges(edges);
    vector<long long> islandSizes;
    // dfs from 0 to n-1
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            response.islandNum++;
            long long nodeCount = 0;
            dfsCore(i, map, visited, nodeCount);
            islandSizes.emplace_back(nodeCount);
        }
    }
    response.islandSizes = islandSizes;
    return response;
}

void CommonUtil::traverseTreeByDepth(const TreeNode *root) {
    queue<const TreeNode *> nodes;
    nodes.push(root);
    while (!nodes.empty()) {
        const TreeNode *node = nodes.front();
        nodes.pop();
        cout << node->val << " ";
        if (node->left != nullptr) {
            nodes.push(node->left);
        }
        if (node->right != nullptr) {
            nodes.push(node->right);
        }
    }
}

void CommonUtil::traverseTreeInOrder(const TreeNode *root) {
    if (root == nullptr) {
        return;
    }
    if (root->left != nullptr) traverseTreeInOrder(root->left);
    cout << root->val << " ";
    if (root->right != nullptr) traverseTreeInOrder(root->right);
}

void CommonUtil::printMetric(const int *metric, const size_t line, const size_t column) {
    for (int i = 0; i < line; i++) {
        for (int j = 0; j < column; j++) {
            cout << *(metric + i * column + j) << " ";
        }
        cout << endl;
    }
}

void CommonUtil::printVectorString(const vector<string> &vec) {
    for (const auto &str: vec) {
        cout << str << endl;
    }
}

void CommonUtil::printMetric(const vector<vector<int> > &metric) {
    for (auto vec: metric) {
        cout << "[";
        for (int i = 0; i < vec.size(); i++) {
            if (i != vec.size() - 1) {
                cout << vec[i] << ", ";
            } else {
                cout << vec[i] << "]" << endl;
            }
        }
    }
}

ListNode *CommonUtil::newList(const vector<int> &nums) {
    if (nums.empty()) {
        return nullptr;
    }
    auto *head = new ListNode(nums[0]);
    auto *tail = head;
    for (int i = 1; i < nums.size(); i++) {
        const int value = nums[i];
        auto node = new ListNode(value);
        tail->next = node;
        tail = node;
    }
    return head;
}

void CommonUtil::printLinkedList(ListNode *head) {
    cout << "[";
    while (head != nullptr) {
        cout << head->val << ", ";
        head = head->next;
    }
    cout << "]" << endl;
}

void CommonUtil::printVectorInt(const vector<int> &vec) {
    for (auto num: vec) {
        cout << num << ", ";
    }
    cout << endl;
}

TreeNode *CommonUtil::buildTreeByDepth(const int nodes[], const int size) {
    TreeNode *nodesP[size + 1];
    for (int i = 1; i < size + 1; i++) {
        if (nodes[i - 1] < 0) {
            nodesP[i] = nullptr;
        } else {
            nodesP[i] = new TreeNode(nodes[i - 1]);
        }
    }
    for (int i = 1; i <= size / 2; i++) {
        const int leftIndex = i * 2;
        const int rightIndex = i * 2 + 1;
        if (leftIndex <= size) {
            nodesP[i]->left = nodesP[leftIndex];
        }
        if (rightIndex <= size) {
            nodesP[i]->right = nodesP[rightIndex];
        }
    }
    return nodesP[1];
}

long long CommonUtil::quickPairSum(const vector<long long>& nums) {
    long long sum = 0;
    long long squaredSum = 0;
    for (const long long num : nums) {
        sum += num;
        squaredSum += num * num;
    }
    return (sum * sum - squaredSum) / 2;
}

void MaxHeap::init() {

}

int MaxHeap::parent(int index) {
    int pi = index / 2;
    return valid(pi) ? pi : -1;
}

int MaxHeap::leftChild(int index) {
    int li = index * 2;
    return valid(li) ? li : -1;
}

int MaxHeap::rightChild(int index) {
    int ri = index * 2 + 1;
    return valid(ri) ? ri : -1;
}

void MaxHeap::swap(int index1, int index2) {
    if (!valid(index1) || !valid(index2)) {
        return;
    }
    const int temp = arr_[index1];
    arr_[index1] = arr_[index2];
    arr_[index2] = temp;
}

bool MaxHeap::valid(int index) {
    return index >= 1 && index < size_;
}

void MaxHeap::insert(int num) {
    if (size_ == max_size_) {
        return;
    }
    int insert_index = size_;
    arr_[insert_index] = num;
    size_++;
    move_up(insert_index);
}

void MaxHeap::pop() {
    if (size_ == 1) {
        return;
    }
    swap(1, size_ - 1);
    arr_.pop_back();
    size_--;
    move_down(1);
}

int MaxHeap::top() {
    if (size_ == 1) {
        return -1;
    }
    return arr_[1];
}

void MaxHeap::move_up(int index) {
    if (!valid(index)) {
        return;
    }
    int i = index;
    while (valid(i)) {
        int pi = parent(i);
        if (!valid(pi) || arr_[i] <= arr_[pi]) {
            break;
        }
        swap(i, pi);
        i = pi;
    }
}

void MaxHeap::move_down(int index) {
    if (!valid(index)) {
        return;
    }
    int i = index;
    while (valid(i)) {
        int maybe_child_index = max_of_three(i);
        if (i == maybe_child_index) {
            break;
        }
        swap(i, maybe_child_index);
        i = maybe_child_index;
    }
}

int MaxHeap::max_of_three(int parent) {
    if (!valid(parent)) {
        return -1;
    }
    int max_index = parent;
    int left_index = leftChild(parent);
    int right_index = rightChild(parent);
    if (valid(left_index) && arr_[left_index] > arr_[max_index]) {
        max_index = left_index;
    }
    if (valid(right_index) && arr_[right_index] > arr_[max_index]) {
        max_index = right_index;
    }
    return max_index;
}
