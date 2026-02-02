#!/bin/bash

# DW Dashboard Management - 功能验证启动脚本

echo "========================================="
echo "  DW Dashboard Management 功能验证"
echo "========================================="
echo ""

# 检查Node.js是否安装
if ! command -v node &> /dev/null; then
    echo "❌ 错误: 未检测到Node.js，请先安装Node.js"
    echo "   下载地址: https://nodejs.org/"
    exit 1
fi

echo "✅ Node.js版本: $(node -v)"
echo "✅ npm版本: $(npm -v)"
echo ""

# 进入前端目录
cd "$(dirname "$0")/../frontend" || exit 1

# 检查依赖是否安装
if [ ! -d "node_modules" ]; then
    echo "📦 正在安装依赖..."
    npm install
    echo ""
fi

echo "========================================="
echo "  启动模式选择"
echo "========================================="
echo ""
echo "1. Mock模式（推荐）- 使用Mock数据，无需后端服务"
echo "2. 正常模式 - 连接真实后端服务"
echo ""
read -p "请选择启动模式 (1/2): " mode

if [ "$mode" = "1" ]; then
    echo ""
    echo "🎭 启动Mock模式..."
    echo ""
    echo "========================================="
    echo "  Mock模式说明"
    echo "========================================="
    echo ""
    echo "✅ 所有API请求将使用Mock数据"
    echo "✅ 无需启动后端服务"
    echo "✅ 数据修改仅在浏览器内存中生效"
    echo ""
    echo "📝 测试账号:"
    echo "   - 管理员: admin / 123456"
    echo "   - 分析师: analyst / 123456"
    echo "   - 查看者: viewer / 123456"
    echo ""
    echo "🌐 访问地址: http://localhost:3000?mock=true"
    echo ""
    echo "========================================="
    echo ""

    # 设置环境变量并启动
    export VITE_USE_MOCK=true
    npm run dev
else
    echo ""
    echo "🚀 启动正常模式..."
    echo ""
    echo "========================================="
    echo "  正常模式说明"
    echo "========================================="
    echo ""
    echo "⚠️  需要先启动后端服务"
    echo "⚠️  后端服务地址: http://localhost:8080"
    echo ""
    echo "🌐 访问地址: http://localhost:3000"
    echo ""
    echo "========================================="
    echo ""

    npm run dev
fi
