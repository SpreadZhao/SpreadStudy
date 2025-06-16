#!/bin/bash

set -e  # 遇到错误就退出
set -o pipefail

# 项目根目录
PROJECT_DIR="$(pwd)"
BUILD_DIR="$PROJECT_DIR/cmake-build-debug"

# Ninja 路径
NINJA_PATH=$(which ninja)

# 自动创建构建目录
mkdir -p "$BUILD_DIR"

# 配置 CMake
cmake -S "$PROJECT_DIR" -B "$BUILD_DIR" \
    -G Ninja \
    -DCMAKE_BUILD_TYPE=Debug \
    -DCMAKE_MAKE_PROGRAM="$NINJA_PATH"

# 编译项目
cmake --build "$BUILD_DIR" -j"$(nproc)"

# 进入构建目录
cd "$BUILD_DIR"

# 自动寻找可执行文件
EXECUTABLE=$(find . -maxdepth 1 -type f -executable \
    ! -name '*CMakeFiles*' ! -name '*.so' ! -name '*.a' | head -n 1)

if [[ -z "$EXECUTABLE" ]]; then
    echo "❌ 未找到可执行文件。"
    exit 1
fi

echo "🚀 运行: $EXECUTABLE"
"$EXECUTABLE"
