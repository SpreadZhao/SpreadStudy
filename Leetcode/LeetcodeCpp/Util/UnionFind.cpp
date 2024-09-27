//
// Created by SpreadZhao on 2024/7/21.
//
#include "UnionFind.h"

UnionFind::UnionFind(int n) {
    parent.resize(n);
    for (int i = 0; i < n; i++) {
        parent[i] = i;
    }
}

void UnionFind::unionSet(int index1, int index2) {
    int root1 = find(index1);
    int root2 = find(index2);
    parent[root1] = root2;
}

int UnionFind::find(int index) {
    while (parent[index] != index) {
        index = parent[index];
    }
    return index;
}