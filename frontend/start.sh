#!/bin/bash

# DW Dashboard Frontend 安装和启动脚本

echo "================================"
echo "DW Dashboard Frontend"
echo "================================"
echo ""

# 进入frontend目录
cd "$(dirname "$0")"

# 检查Node.js是否安装
if ! command -v node &> /dev/null; then
    echo "错误: 未检测到Node.js,请先安装Node.js (>= 16)"
    exit 1
fi

echo "Node.js版本: $(node -v)"
echo "npm版本: $(npm -v)"
echo ""

# 安装依赖
if [ ! -d "node_modules" ]; then
    echo "正在安装依赖..."
    npm install
    if [ $? -ne 0 ]; then
        echo "错误: 依赖安装失败"
        exit 1
    fi
    echo "依赖安装完成!"
    echo ""
else
    echo "依赖已安装,跳过安装步骤"
    echo ""
fi

# 启动开发服务器
echo "正在启动开发服务器..."
echo "访问地址: http://localhost:3000"
echo ""
npm run dev
