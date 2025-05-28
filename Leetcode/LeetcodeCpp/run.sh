#!/bin/bash

# 项目根目录
PROJECT_DIR="$(pwd)"

# 构建目录
BUILD_DIR="$PROJECT_DIR/cmake-build-debug"

# 如果构建目录不存在就报错退出
if [ ! -d "$BUILD_DIR" ]; then
    echo "❌ 构建目录 $BUILD_DIR 不存在。请先构建一次或在 CLion 中生成。"
    exit 1
fi

# 配置 CMake（在源码目录执行）
cmake -S "$PROJECT_DIR" -B "$BUILD_DIR" \
    -G Ninja \
    -DCMAKE_BUILD_TYPE=Debug \
    -DCMAKE_MAKE_PROGRAM=/home/spreadzhao/.local/share/JetBrains/Toolbox/apps/clion/bin/ninja/linux/x64/ninja

# 编译项目
cmake --build "$BUILD_DIR" -j$(nproc)
if [ $? -ne 0 ]; then
    echo "❌ 编译失败。"
    exit 1
fi

# 进入构建目录
cd "$BUILD_DIR" || exit 1

# 找到可执行文件（排除 CMakeFiles 等无关文件）
EXECUTABLE=$(find . -maxdepth 1 -type f -executable ! -name '*CMakeFiles*' ! -name '*.so' ! -name '*.a' | head -n 1)

if [ -z "$EXECUTABLE" ]; then
    echo "❌ 未找到可执行文件。"
    exit 1
fi

echo "🚀 运行: $EXECUTABLE"
"$EXECUTABLE"
