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

# 获取脚本所在目录的绝对路径
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
FRONTEND_DIR="$PROJECT_ROOT/frontend"

echo "📂 项目目录: $PROJECT_ROOT"
echo "📂 前端目录: $FRONTEND_DIR"
echo ""

# 检查前端目录是否存在
if [ ! -d "$FRONTEND_DIR" ]; then
    echo "❌ 错误: 前端目录不存在: $FRONTEND_DIR"
    exit 1
fi

# 进入前端目录
cd "$FRONTEND_DIR" || exit 1

# 检查依赖是否安装
if [ ! -d "node_modules" ]; then
    echo "📦 检测到依赖未安装，正在安装..."
    echo ""
    echo "⚠️  如果遇到权限问题，请执行以下命令修复："
    echo "   sudo chown -R \$(whoami) ~/.npm"
    echo ""

    # 尝试安装依赖
    if npm install; then
        echo ""
        echo "✅ 依赖安装成功！"
        echo ""
    else
        echo ""
        echo "❌ 依赖安装失败！"
        echo ""
        echo "请尝试以下解决方案："
        echo "1. 修复npm权限："
        echo "   sudo chown -R \$(whoami) ~/.npm"
        echo "   npm cache clean --force"
        echo ""
        echo "2. 或者使用yarn："
        echo "   npm install -g yarn"
        echo "   yarn install"
        echo ""
        echo "3. 或者直接打开独立验证页面："
        echo "   open $PROJECT_ROOT/功能验证页面.html"
        echo ""
        exit 1
    fi
fi

echo "========================================="
echo "  启动模式选择"
echo "========================================="
echo ""
echo "1. Mock模式（推荐）- 使用Mock数据，无需后端服务"
echo "2. 正常模式 - 连接真实后端服务"
echo "3. 打开独立验证页面 - 无需npm依赖"
echo ""
read -p "请选择启动模式 (1/2/3): " mode

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
    echo "🌐 启动后请访问: http://localhost:3000?mock=true"
    echo ""
    echo "💡 提示: 请在URL中添加 ?mock=true 参数"
    echo ""
    echo "========================================="
    echo ""

    # 设置环境变量并启动
    export VITE_USE_MOCK=true
    npm run dev

elif [ "$mode" = "2" ]; then
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

elif [ "$mode" = "3" ]; then
    echo ""
    echo "📄 打开独立验证页面..."
    echo ""
    echo "========================================="
    echo "  独立验证页面说明"
    echo "========================================="
    echo ""
    echo "✅ 无需npm依赖"
    echo "✅ 无需后端服务"
    echo "✅ 直接在浏览器中运行"
    echo "✅ 包含所有功能演示"
    echo ""
    echo "📝 测试账号:"
    echo "   - 管理员: admin / 123456"
    echo "   - 分析师: analyst / 123456"
    echo "   - 查看者: viewer / 123456"
    echo ""
    echo "========================================="
    echo ""

    # 打开独立验证页面
    if [ -f "$PROJECT_ROOT/功能验证页面.html" ]; then
        open "$PROJECT_ROOT/功能验证页面.html"
        echo "✅ 已在浏览器中打开验证页面"
    else
        echo "❌ 错误: 找不到验证页面文件"
        echo "   文件路径: $PROJECT_ROOT/功能验证页面.html"
    fi

else
    echo ""
    echo "❌ 无效的选择，请输入 1、2 或 3"
    exit 1
fi
