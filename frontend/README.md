# DW Dashboard Frontend

DW Dashboard Management 自助报表管理系统前端项目

## 技术栈

- **框架**: React 18
- **语言**: TypeScript
- **构建工具**: Vite 5
- **UI组件库**: Ant Design 5
- **图表库**: ECharts 5
- **状态管理**: Zustand
- **路由**: React Router 6
- **HTTP客户端**: Axios
- **工具库**: dayjs, lodash-es, ahooks

## 项目结构

```
frontend/
├── src/
│   ├── api/                    # API接口封装
│   │   ├── auth.ts            # 认证接口
│   │   ├── user.ts            # 用户管理接口
│   │   ├── datasource.ts      # 数据源管理接口
│   │   ├── dashboard.ts       # 报表管理接口
│   │   └── permission.ts      # 权限管理接口
│   ├── assets/                 # 静态资源
│   ├── components/             # 通用组件
│   │   ├── Layout/            # 布局组件
│   │   │   └── MainLayout.tsx
│   │   ├── Common/            # 通用组件
│   │   │   ├── Loading.tsx
│   │   │   └── ErrorBoundary.tsx
│   │   └── Charts/            # 图表组件
│   │       └── Chart.tsx
│   ├── pages/                  # 页面组件
│   │   ├── Login/             # 登录页面
│   │   ├── Dashboard/         # 报表管理
│   │   │   ├── List.tsx       # 报表列表
│   │   │   ├── Detail.tsx     # 报表详情
│   │   │   └── View.tsx       # 报表查看
│   │   ├── Designer/          # 报表设计器
│   │   ├── DataSource/        # 数据源管理
│   │   │   ├── List.tsx       # 数据源列表
│   │   │   └── Create.tsx     # 创建/编辑数据源
│   │   ├── User/              # 用户管理
│   │   │   ├── List.tsx       # 用户列表
│   │   │   └── RoleManage.tsx # 角色管理
│   │   └── Permission/        # 权限管理
│   ├── store/                  # 状态管理
│   │   ├── auth.ts            # 认证状态
│   │   ├── user.ts            # 用户状态
│   │   └── dashboard.ts       # 报表状态
│   ├── hooks/                  # 自定义Hooks
│   ├── utils/                  # 工具函数
│   │   ├── request.ts         # Axios封装
│   │   ├── storage.ts         # 本地存储封装
│   │   └── format.ts          # 格式化工具
│   ├── types/                  # TypeScript类型定义
│   │   └── index.ts
│   ├── router/                 # 路由配置
│   │   └── index.tsx
│   ├── App.tsx                 # 应用根组件
│   ├── main.tsx                # 应用入口
│   └── index.css               # 全局样式
├── public/                     # 公共资源
├── .env.development            # 开发环境配置
├── .env.production             # 生产环境配置
├── .eslintrc.cjs               # ESLint配置
├── .prettierrc                 # Prettier配置
├── .gitignore                  # Git忽略文件
├── index.html                  # HTML模板
├── package.json                # 项目依赖
├── tsconfig.json               # TypeScript配置
├── tsconfig.node.json          # Node TypeScript配置
├── vite.config.ts              # Vite配置
└── README.md                   # 项目说明
```

## 功能模块

### 1. 用户认证
- 登录/登出
- Token管理
- 路由守卫

### 2. 报表管理
- 报表列表查看
- 报表详情查看
- 报表创建/编辑
- 报表发布/下线
- 报表删除
- 报表查询执行

### 3. 报表设计器
- 基本信息配置
- 数据源选择
- SQL编写
- 查询参数配置
- 展示配置
- 图表类型选择

### 4. 数据源管理
- 数据源列表
- 数据源创建/编辑
- 数据源连接测试
- 数据源状态管理
- 支持多种数据库类型

### 5. 用户管理
- 用户列表
- 用户创建/编辑
- 用户状态管理
- 角色分配
- 角色管理

### 6. 权限管理
- 权限列表
- 授予用户权限
- 授予角色权限
- 撤销权限
- 权限级别控制

## 快速开始

### 1. 安装依赖

```bash
cd frontend
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

应用将在 http://localhost:3000 启动

### 3. 构建生产版本

```bash
npm run build
```

构建产物将输出到 `dist` 目录

### 4. 预览生产版本

```bash
npm run preview
```

## 环境配置

### 开发环境 (.env.development)

```env
VITE_APP_TITLE=DW Dashboard Management
VITE_APP_BASE_API=/api
VITE_APP_PORT=3000
```

### 生产环境 (.env.production)

```env
VITE_APP_TITLE=DW Dashboard Management
VITE_APP_BASE_API=/api
```

## API代理配置

开发环境下,Vite会将 `/api` 开头的请求代理到后端服务器 `http://localhost:8080`

配置位置: `vite.config.ts`

```typescript
server: {
  port: 3000,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
    },
  },
}
```

## 代码规范

### ESLint

```bash
npm run lint
```

### Prettier

项目已配置Prettier,建议在IDE中启用自动格式化

## 默认账号

- 用户名: admin
- 密码: 123456

## 主要依赖版本

- react: ^18.2.0
- react-dom: ^18.2.0
- react-router-dom: ^6.20.0
- antd: ^5.12.0
- @ant-design/pro-components: ^2.6.43
- echarts: ^5.4.3
- echarts-for-react: ^3.0.2
- axios: ^1.6.2
- zustand: ^4.4.7
- dayjs: ^1.11.10
- lodash-es: ^4.17.21
- ahooks: ^3.7.8
- typescript: ^5.2.2
- vite: ^5.0.8

## 浏览器支持

- Chrome >= 90
- Firefox >= 88
- Safari >= 14
- Edge >= 90

## 开发建议

1. 使用TypeScript编写代码,充分利用类型检查
2. 遵循React Hooks最佳实践
3. 组件保持单一职责
4. 合理使用状态管理,避免过度使用全局状态
5. API调用统一使用封装的http工具
6. 错误处理要完善,给用户友好的提示
7. 注意性能优化,使用React.memo、useMemo、useCallback等
8. 代码提交前运行lint检查

## 常见问题

### 1. 启动失败

检查Node.js版本是否 >= 16,建议使用Node.js 18+

### 2. API请求失败

检查后端服务是否启动,确认API地址配置正确

### 3. 登录后跳转失败

检查Token是否正确存储,查看浏览器控制台错误信息

### 4. 图表不显示

检查ECharts配置是否正确,数据格式是否符合要求

## 许可证

MIT License

## 联系方式

如有问题或建议,请提交Issue或Pull Request
