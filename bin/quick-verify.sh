#!/bin/bash

# 快速打开功能验证页面

echo "🎯 DW Dashboard Management - 快速验证"
echo ""

# 获取脚本所在目录
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
HTML_FILE="$PROJECT_ROOT/功能验证页面.html"

# 检查文件是否存在
if [ ! -f "$HTML_FILE" ]; then
    echo "❌ 错误: 找不到验证页面文件"
    echo "   文件路径: $HTML_FILE"
    exit 1
fi

echo "📄 正在打开功能验证页面..."
echo ""
echo "📝 测试账号:"
echo "   - 管理员: admin / 123456"
echo "   - 分析师: analyst / 123456"
echo "   - 查看者: viewer / 123456"
echo ""

# 打开HTML文件
open "$HTML_FILE"

echo "✅ 已在浏览器中打开验证页面"
echo ""
echo "💡 提示: 这是一个独立的HTML页面，包含所有功能演示"
echo "   所有操作仅在浏览器内存中生效，刷新页面后数据会重置"
