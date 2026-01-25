#!/usr/bin/env bash

set -e
set -o pipefail

# ======================
# 参数解析
# ======================
CLEAN=0
while getopts ":c" opt; do
    case "$opt" in
        c)
            CLEAN=1
            ;;
        *)
            echo "Usage: $0 [-c]"
            exit 1
            ;;
    esac
done

# ======================
# 项目路径
# ======================
PROJECT_DIR="$(pwd)"
BUILD_DIR="$PROJECT_DIR/cmake-build-debug"

# Ninja 路径
NINJA_PATH=$(which ninja)

# ======================
# clean 逻辑
# ======================
if [[ $CLEAN -eq 1 ]]; then
    echo "🧹 清理构建目录: $BUILD_DIR"
    rm -rf "$BUILD_DIR"
fi

# 自动创建构建目录
mkdir -p "$BUILD_DIR"

# ======================
# 配置 CMake
# ======================
cmake -S "$PROJECT_DIR" -B "$BUILD_DIR" \
    -G Ninja \
    -DCMAKE_BUILD_TYPE=Debug \
    -DCMAKE_MAKE_PROGRAM="$NINJA_PATH"

# 编译项目
cmake --build "$BUILD_DIR" -j"$(nproc)"

# 进入构建目录
cd "$BUILD_DIR"

# ======================
# 查找可执行文件
# ======================
EXECUTABLE=$(find . -maxdepth 1 -type f -executable \
    ! -name '*CMakeFiles*' ! -name '*.so' ! -name '*.a' | head -n 1)

if [[ -z "$EXECUTABLE" ]]; then
    echo "❌ 未找到可执行文件。"
    exit 1
fi

echo "🚀 运行: $EXECUTABLE"
"$EXECUTABLE"
