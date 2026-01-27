# Git仓库初始化说明

由于权限限制,前端项目目前位于后端项目的frontend子目录中。

如果您想将前端项目设置为独立的Git仓库,请执行以下步骤:

## 方案1: 将frontend移动到独立目录

```bash
# 1. 回到父目录
cd /Users/zengrongjun/claudespace/

# 2. 复制frontend目录到独立位置
cp -r dw-dashboard-management/frontend dw-dashboard-frontend

# 3. 进入新目录
cd dw-dashboard-frontend

# 4. 初始化Git仓库
git init

# 5. 添加所有文件
git add .

# 6. 创建首次提交
git commit -m "Initial commit: DW-Dashboard-Frontend 自助报表管理系统前端"

# 7. 添加远程仓库(可选)
git remote add origin <your-git-repository-url>

# 8. 推送到远程仓库(可选)
git push -u origin main
```

## 方案2: 在当前位置初始化Git(子模块方式)

```bash
# 1. 进入frontend目录
cd /Users/zengrongjun/claudespace/dw-dashboard-management/frontend

# 2. 初始化Git仓库
git init

# 3. 添加所有文件
git add .

# 4. 创建首次提交
git commit -m "Initial commit: DW-Dashboard-Frontend 自助报表管理系统前端"

# 5. 在后端项目中添加.gitignore规则(避免重复追踪)
echo "frontend/" >> ../.gitignore
```

## 项目信息

- 项目名称: DW-Dashboard-Frontend
- 技术栈: React 18 + TypeScript + Vite + Ant Design
- 项目类型: 自助报表管理系统前端
- 开发语言: TypeScript
- 构建工具: Vite

## 注意事项

1. 如果选择方案1,建议在复制完成后删除原来的frontend目录
2. 如果选择方案2,后端项目将不会追踪frontend目录的变更
3. 建议创建.env.local文件存储本地配置,不要提交到Git
