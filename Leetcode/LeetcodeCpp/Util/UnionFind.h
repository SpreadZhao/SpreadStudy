//
// Created by SpreadZhao on 2024/7/21.
//

#ifndef LEETCODECPP_UNIONFIND_H
#define LEETCODECPP_UNIONFIND_H

#include "../common.h"

class UnionFind {
public:
    vector<int> parent;
    explicit UnionFind(int n);
    void unionSet(int index1, int index2);
    int find(int index);
};


#endif //LEETCODECPP_UNIONFIND_H
