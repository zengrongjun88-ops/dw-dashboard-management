# DW Dashboard Frontend - 快速参考指南

## 快速启动

```bash
# 进入项目目录
cd /Users/zengrongjun/claudespace/dw-dashboard-management/frontend

# 安装依赖(首次运行)
npm install

# 启动开发服务器
npm run dev

# 访问地址: http://localhost:3000
# 默认账号: admin / 123456
```

## 常用命令

```bash
# 开发
npm run dev          # 启动开发服务器

# 构建
npm run build        # 构建生产版本
npm run preview      # 预览生产版本

# 代码检查
npm run lint         # ESLint检查
```

## 项目结构速查

```
frontend/
├── src/
│   ├── api/              # API接口
│   ├── components/       # 组件
│   ├── pages/            # 页面
│   ├── store/            # 状态
│   ├── router/           # 路由
│   ├── utils/            # 工具
│   └── types/            # 类型
├── package.json          # 依赖
├── vite.config.ts        # Vite配置
└── tsconfig.json         # TS配置
```

## 路由速查

| 路径 | 页面 | 说明 |
|------|------|------|
| /login | 登录页 | 用户登录 |
| /dashboard | 报表列表 | 查看所有报表 |
| /dashboard/view/:id | 报表查看 | 查看报表数据 |
| /designer | 报表设计器 | 创建报表 |
| /designer/:id | 报表编辑 | 编辑报表 |
| /datasource | 数据源列表 | 管理数据源 |
| /user | 用户列表 | 管理用户 |
| /permission | 权限管理 | 管理权限 |

## API速查

### 认证 (auth.ts)
- login - 登录
- logout - 登出
- getCurrentUser - 获取当前用户

### 报表 (dashboard.ts)
- getDashboardList - 获取列表
- getDashboardDetail - 获取详情
- createDashboard - 创建
- updateDashboard - 更新
- executeDashboard - 执行查询

### 数据源 (datasource.ts)
- getDataSourceList - 获取列表
- createDataSource - 创建
- testDataSourceConnection - 测试连接

### 用户 (user.ts)
- getUserList - 获取列表
- createUser - 创建
- updateUser - 更新

### 权限 (permission.ts)
- getPermissionList - 获取列表
- grantUserPermission - 授予用户权限
- grantRolePermission - 授予角色权限

## 状态管理速查

### useAuthStore
```typescript
const { token, user, isAuthenticated, login, logout } = useAuthStore()
```

### useUserStore
```typescript
const { users, roles, setUsers, setRoles } = useUserStore()
```

### useDashboardStore
```typescript
const { dashboards, dataSources, setDashboards } = useDashboardStore()
```

## 工具函数速查

### HTTP请求 (request.ts)
```typescript
import { http } from '@/utils/request'

http.get(url)
http.post(url, data)
http.put(url, data)
http.delete(url)
```

### 本地存储 (storage.ts)
```typescript
import { getToken, setToken, removeToken } from '@/utils/storage'
```

### 格式化 (format.ts)
```typescript
import { formatDate, formatDateTime, formatNumber } from '@/utils/format'
```

## 组件速查

### 布局
```typescript
import MainLayout from '@/components/Layout/MainLayout'
```

### 通用
```typescript
import Loading from '@/components/Common/Loading'
import ErrorBoundary from '@/components/Common/ErrorBoundary'
```

### 图表
```typescript
import Chart from '@/components/Charts/Chart'
```

## 配置速查

### 环境变量
```env
VITE_APP_TITLE=DW Dashboard Management
VITE_APP_BASE_API=/api
VITE_APP_PORT=3000
```

### 路径别名
```typescript
import xxx from '@/xxx'  // @ = src/
```

### API代理
```typescript
// vite.config.ts
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
  },
}
```

## 常见问题速查

### 1. 端口被占用
```bash
# 修改 .env.development
VITE_APP_PORT=3001
```

### 2. API请求失败
- 检查后端服务是否启动(端口8080)
- 检查vite.config.ts中的proxy配置

### 3. 登录失败
- 确认后端服务正常
- 检查用户名密码(admin/123456)
- 查看浏览器控制台错误

### 4. 依赖安装失败
```bash
# 清除缓存重新安装
rm -rf node_modules package-lock.json
npm install
```

## 开发建议

1. 使用TypeScript,充分利用类型检查
2. 遵循React Hooks最佳实践
3. 组件保持单一职责
4. 合理使用状态管理
5. API调用统一使用http工具
6. 错误处理要完善
7. 注意性能优化

## 文档索引

- README.md - 项目说明
- INSTALL.md - 安装指南
- PROJECT_SUMMARY.md - 项目总结
- VERIFICATION.md - 验证报告
- GIT_INIT.md - Git初始化
- QUICK_REFERENCE.md - 本文档

## 技术支持

如有问题,请查看:
1. 项目文档
2. 浏览器控制台
3. 后端日志
4. GitHub Issues
