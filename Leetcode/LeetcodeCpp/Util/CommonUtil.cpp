//
// Created by SpreadZhao on 2024/8/4.
//
#include "CommonUtil.h"

bool CommonUtil::isEven(int n) { return n % 2 == 0; }

map<int, vector<int>> buildMapForEdges(const vector<vector<int>> &edges) {
    map<int, vector<int>> m;
    for (auto &edge : edges) {
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

void dfsCore(int start, const map<int, vector<int>>& edgesMap, bool visited[]) {
    if (visited[start]) {
        return;
    }
    visited[start] = true;
    if (!edgesMap.count(start)) {
        return;
    }
    for (const auto neighbor : edgesMap.at(start)) {
        if (!visited[neighbor]) {
            dfsCore(neighbor, edgesMap, visited);
        }
    }
}

CommonUtil::DFSResponse CommonUtil::dfs(const int n, const vector<vector<int>>& edges) {
    DFSResponse response;
    bool visited[n];
    fill_n(visited, n, false);
    const auto map = buildMapForEdges(edges);
    // dfs from 0 to n-1
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            response.islandNum++;
            dfsCore(i, map, visited);
        }
    }
    return response;
}

void CommonUtil::traverseTreeByDepth(const TreeNode *root) {
    queue<const TreeNode*> nodes;
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

void CommonUtil::printMetric(const int *metric, const size_t line, const size_t column) {
    for (int i = 0; i < line; i++) {
        for (int j = 0; j < column; j++) {
            cout << *(metric + i * column + j) << " ";
        }
        cout << endl;
    }
}

void CommonUtil::printVectorString(const vector<string> &vec) {
    for (const auto& str : vec) {
        cout << str << endl;
    }
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

