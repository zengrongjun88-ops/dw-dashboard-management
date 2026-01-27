# DW Dashboard Frontend - 安装和启动指南

## 快速开始

### 方法1: 使用启动脚本(推荐)

```bash
cd frontend
chmod +x start.sh
./start.sh
```

### 方法2: 手动安装和启动

```bash
# 1. 进入frontend目录
cd frontend

# 2. 安装依赖
npm install

# 3. 启动开发服务器
npm run dev
```

## 访问地址

开发服务器启动后,在浏览器中访问:

```
http://localhost:3000
```

## 默认登录账号

- 用户名: admin
- 密码: 123456

## 其他命令

### 构建生产版本

```bash
npm run build
```

### 预览生产版本

```bash
npm run preview
```

### 代码检查

```bash
npm run lint
```

## 注意事项

1. 确保已安装Node.js (>= 16),推荐使用Node.js 18+
2. 确保后端服务已启动(默认端口8080)
3. 如果端口3000被占用,可以修改.env.development中的VITE_APP_PORT配置

## 常见问题

### 1. 依赖安装失败

尝试清除缓存后重新安装:

```bash
rm -rf node_modules package-lock.json
npm install
```

### 2. 启动失败

检查端口是否被占用:

```bash
lsof -i :3000
```

### 3. API请求失败

确认后端服务是否启动,检查vite.config.ts中的proxy配置是否正确
