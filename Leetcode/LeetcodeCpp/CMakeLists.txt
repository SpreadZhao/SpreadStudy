cmake_minimum_required(VERSION 3.28)
project(LeetcodeCpp)

set(CMAKE_CXX_STANDARD 11)

add_executable(LeetcodeCpp main.cpp
        common.h
        Questions/Solution.h
        Questions/Solution.cpp
        Util/UnionFind.h
        Util/UnionFind.cpp
        Util/CommonUtil.cpp
        Util/CommonUtil.h)
