# 前端实现指南

## 一、项目初始化

### 1.1 创建项目

```bash
# 进入工作目录
cd /Users/zengrongjun/claudespace

# 创建Vite + React + TypeScript项目
npm create vite@latest dw-dashboard-frontend -- --template react-ts

# 进入项目目录
cd dw-dashboard-frontend
```

### 1.2 安装依赖

```bash
# 安装核心依赖
npm install react@18 react-dom@18 react-router-dom@6

# 安装UI组件库
npm install antd@5 @ant-design/pro-components@2

# 安装图表库
npm install echarts@5 echarts-for-react

# 安装HTTP客户端和状态管理
npm install axios zustand

# 安装工具库
npm install dayjs lodash-es ahooks

# 安装开发依赖
npm install -D @types/node @types/lodash-es
npm install -D eslint prettier
npm install -D @typescript-eslint/parser @typescript-eslint/eslint-plugin
```

### 1.3 项目结构

```
dw-dashboard-frontend/
├── public/
│   └── vite.svg
├── src/
│   ├── api/                    # API接口封装
│   │   ├── auth.ts
│   │   ├── user.ts
│   │   ├── datasource.ts
│   │   ├── dashboard.ts
│   │   └── permission.ts
│   ├── assets/                 # 静态资源
│   │   └── react.svg
│   ├── components/             # 通用组件
│   │   ├── Layout/
│   │   │   ├── index.tsx
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   └── Footer.tsx
│   │   ├── Common/
│   │   │   ├── Loading.tsx
│   │   │   └── ErrorBoundary.tsx
│   │   └── Charts/
│   │       ├── LineChart.tsx
│   │       ├── BarChart.tsx
│   │       └── PieChart.tsx
│   ├── pages/                  # 页面组件
│   │   ├── Login/
│   │   │   └── index.tsx
│   │   ├── Dashboard/
│   │   │   ├── List.tsx
│   │   │   ├── Detail.tsx
│   │   │   └── View.tsx
│   │   ├── Designer/
│   │   │   └── index.tsx
│   │   ├── DataSource/
│   │   │   ├── List.tsx
│   │   │   └── Create.tsx
│   │   ├── User/
│   │   │   ├── List.tsx
│   │   │   └── RoleManage.tsx
│   │   └── Permission/
│   │       └── Manage.tsx
│   ├── store/                  # 状态管理
│   │   ├── auth.ts
│   │   ├── user.ts
│   │   └── dashboard.ts
│   ├── hooks/                  # 自定义Hooks
│   │   ├── useAuth.ts
│   │   └── usePermission.ts
│   ├── utils/                  # 工具函数
│   │   ├── request.ts
│   │   ├── storage.ts
│   │   └── format.ts
│   ├── types/                  # TypeScript类型
│   │   ├── api.d.ts
│   │   ├── user.d.ts
│   │   └── dashboard.d.ts
│   ├── router/                 # 路由配置
│   │   └── index.tsx
│   ├── App.tsx
│   ├── App.css
│   ├── main.tsx
│   └── vite-env.d.ts
├── .env.development
├── .env.production
├── .eslintrc.cjs
├── .gitignore
├── index.html
├── package.json
├── tsconfig.json
├── tsconfig.node.json
├── vite.config.ts
└── README.md
```

---

## 二、核心功能实现

### 2.1 工具函数

#### utils/request.ts - Axios封装

```typescript
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { message } from 'antd';
import { useAuthStore } from '../store/auth';

// 创建axios实例
const request: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // 添加Token
    const token = useAuthStore.getState().token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const { data } = response;

    // 统一响应格式处理
    if (data.code === 200) {
      return data.data;
    }

    // Token过期
    if (data.code === 401) {
      message.error('登录已过期，请重新登录');
      useAuthStore.getState().logout();
      window.location.href = '/login';
      return Promise.reject(new Error('Token expired'));
    }

    // 其他错误
    message.error(data.message || '请求失败');
    return Promise.reject(new Error(data.message || '请求失败'));
  },
  (error) => {
    if (error.response) {
      const { status, data } = error.response;

      switch (status) {
        case 401:
          message.error('未授权，请登录');
          useAuthStore.getState().logout();
          window.location.href = '/login';
          break;
        case 403:
          message.error('没有权限访问');
          break;
        case 404:
          message.error('请求的资源不存在');
          break;
        case 500:
          message.error('服务器错误');
          break;
        default:
          message.error(data?.message || '请求失败');
      }
    } else if (error.request) {
      message.error('网络错误，请检查网络连接');
    } else {
      message.error('请求配置错误');
    }

    return Promise.reject(error);
  }
);

export default request;
```

#### utils/storage.ts - 本地存储封装

```typescript
const TOKEN_KEY = 'dw_dashboard_token';
const USER_KEY = 'dw_dashboard_user';

export const storage = {
  // Token操作
  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  },

  setToken(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
  },

  removeToken(): void {
    localStorage.removeItem(TOKEN_KEY);
  },

  // 用户信息操作
  getUser(): any {
    const userStr = localStorage.getItem(USER_KEY);
    return userStr ? JSON.parse(userStr) : null;
  },

  setUser(user: any): void {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  },

  removeUser(): void {
    localStorage.removeItem(USER_KEY);
  },

  // 清除所有
  clear(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  },
};
```

#### utils/format.ts - 格式化工具

```typescript
import dayjs from 'dayjs';

export const format = {
  // 日期格式化
  date(date: string | Date, pattern = 'YYYY-MM-DD HH:mm:ss'): string {
    return dayjs(date).format(pattern);
  },

  // 数字格式化
  number(num: number, decimals = 2): string {
    return num.toFixed(decimals);
  },

  // 文件大小格式化
  fileSize(bytes: number): string {
    if (bytes === 0) return '0 B';
    const k = 1024;
    const sizes = ['B', 'KB', 'MB', 'GB', 'TB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i];
  },

  // 百分比格式化
  percent(value: number, total: number, decimals = 2): string {
    if (total === 0) return '0%';
    return ((value / total) * 100).toFixed(decimals) + '%';
  },
};
```

### 2.2 API接口封装

#### api/auth.ts - 认证API

```typescript
import request from '../utils/request';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  refreshToken: string;
  user: {
    id: number;
    username: string;
    realName: string;
    email: string;
    roles: string[];
  };
}

export const authApi = {
  // 登录
  login(data: LoginRequest): Promise<LoginResponse> {
    return request.post('/auth/login', data);
  },

  // 登出
  logout(): Promise<void> {
    return request.post('/auth/logout');
  },

  // 刷新Token
  refreshToken(refreshToken: string): Promise<{ token: string }> {
    return request.post('/auth/refresh', { refreshToken });
  },

  // 获取当前用户信息
  getCurrentUser(): Promise<any> {
    return request.get('/auth/current');
  },
};
```

#### api/user.ts - 用户API

```typescript
import request from '../utils/request';

export const userApi = {
  // 获取用户列表
  getList(params: any): Promise<any> {
    return request.get('/user/list', { params });
  },

  // 获取用户详情
  getDetail(id: number): Promise<any> {
    return request.get(`/user/detail/${id}`);
  },

  // 创建用户
  create(data: any): Promise<any> {
    return request.post('/user/create', data);
  },

  // 更新用户状态
  updateStatus(id: number, status: number): Promise<any> {
    return request.put(`/user/status/${id}`, { status });
  },

  // 删除用户
  delete(id: number): Promise<any> {
    return request.delete(`/user/delete/${id}`);
  },
};
```

#### api/datasource.ts - 数据源API

```typescript
import request from '../utils/request';

export const datasourceApi = {
  // 获取数据源列表
  getList(params: any): Promise<any> {
    return request.get('/datasource/list', { params });
  },

  // 获取数据源详情
  getDetail(id: number): Promise<any> {
    return request.get(`/datasource/detail/${id}`);
  },

  // 创建数据源
  create(data: any): Promise<any> {
    return request.post('/datasource/create', data);
  },

  // 测试数据源连接
  test(data: any): Promise<any> {
    return request.post('/datasource/test', data);
  },

  // 更新数据源状态
  updateStatus(id: number, status: number): Promise<any> {
    return request.put(`/datasource/status/${id}`, { status });
  },

  // 删除数据源
  delete(id: number): Promise<any> {
    return request.delete(`/datasource/delete/${id}`);
  },
};
```

#### api/dashboard.ts - 报表API

```typescript
import request from '../utils/request';

export const dashboardApi = {
  // 获取报表列表
  getList(params: any): Promise<any> {
    return request.get('/dashboard/list', { params });
  },

  // 获取报表详情
  getDetail(id: number): Promise<any> {
    return request.get(`/dashboard/detail/${id}`);
  },

  // 创建报表
  create(data: any): Promise<any> {
    return request.post('/dashboard/create', data);
  },

  // 发布报表
  publish(id: number): Promise<any> {
    return request.put(`/dashboard/publish/${id}`);
  },

  // 下线报表
  offline(id: number): Promise<any> {
    return request.put(`/dashboard/offline/${id}`);
  },

  // 删除报表
  delete(id: number): Promise<any> {
    return request.delete(`/dashboard/delete/${id}`);
  },

  // 执行报表查询
  execute(data: any): Promise<any> {
    return request.post('/dashboard/execute', data);
  },

  // 清除报表缓存
  clearCache(dashboardId: number): Promise<any> {
    return request.delete(`/dashboard/cache/${dashboardId}`);
  },
};
```

### 2.3 状态管理

#### store/auth.ts - 认证状态

```typescript
import { create } from 'zustand';
import { storage } from '../utils/storage';
import { authApi, LoginRequest } from '../api/auth';

interface AuthState {
  token: string | null;
  user: any;
  isAuthenticated: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
  refreshToken: () => Promise<void>;
  setUser: (user: any) => void;
}

export const useAuthStore = create<AuthState>((set, get) => ({
  token: storage.getToken(),
  user: storage.getUser(),
  isAuthenticated: !!storage.getToken(),

  login: async (credentials) => {
    const response = await authApi.login(credentials);
    storage.setToken(response.token);
    storage.setUser(response.user);
    set({
      token: response.token,
      user: response.user,
      isAuthenticated: true,
    });
  },

  logout: () => {
    authApi.logout().catch(() => {});
    storage.clear();
    set({
      token: null,
      user: null,
      isAuthenticated: false,
    });
  },

  refreshToken: async () => {
    const { token } = get();
    if (!token) return;

    try {
      const response = await authApi.refreshToken(token);
      storage.setToken(response.token);
      set({ token: response.token });
    } catch (error) {
      get().logout();
    }
  },

  setUser: (user) => {
    storage.setUser(user);
    set({ user });
  },
}));
```

### 2.4 路由配置

#### router/index.tsx

```typescript
import { createBrowserRouter, Navigate } from 'react-router-dom';
import { useAuthStore } from '../store/auth';
import Layout from '../components/Layout';
import Login from '../pages/Login';
import DashboardList from '../pages/Dashboard/List';
import DashboardView from '../pages/Dashboard/View';
import DataSourceList from '../pages/DataSource/List';
import DataSourceCreate from '../pages/DataSource/Create';
import UserList from '../pages/User/List';
import RoleManage from '../pages/User/RoleManage';

// 路由守卫
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated);

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
};

export const router = createBrowserRouter([
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/',
    element: (
      <ProtectedRoute>
        <Layout />
      </ProtectedRoute>
    ),
    children: [
      {
        index: true,
        element: <Navigate to="/dashboard/list" replace />,
      },
      {
        path: 'dashboard',
        children: [
          {
            path: 'list',
            element: <DashboardList />,
          },
          {
            path: 'view/:id',
            element: <DashboardView />,
          },
        ],
      },
      {
        path: 'datasource',
        children: [
          {
            path: 'list',
            element: <DataSourceList />,
          },
          {
            path: 'create',
            element: <DataSourceCreate />,
          },
        ],
      },
      {
        path: 'user',
        children: [
          {
            path: 'list',
            element: <UserList />,
          },
          {
            path: 'role',
            element: <RoleManage />,
          },
        ],
      },
    ],
  },
]);
```

---

## 三、页面组件实现

### 3.1 登录页面

#### pages/Login/index.tsx

```typescript
import React from 'react';
import { Form, Input, Button, Card, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '../../store/auth';
import './index.css';

const Login: React.FC = () => {
  const navigate = useNavigate();
  const login = useAuthStore((state) => state.login);
  const [loading, setLoading] = React.useState(false);

  const onFinish = async (values: any) => {
    setLoading(true);
    try {
      await login(values);
      message.success('登录成功');
      navigate('/');
    } catch (error) {
      message.error('登录失败，请检查用户名和密码');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <Card title="DW Dashboard 登录" className="login-card">
        <Form
          name="login"
          onFinish={onFinish}
          autoComplete="off"
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名' }]}
          >
            <Input
              prefix={<UserOutlined />}
              placeholder="用户名"
              size="large"
            />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="密码"
              size="large"
            />
          </Form.Item>

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              loading={loading}
              block
              size="large"
            >
              登录
            </Button>
          </Form.Item>
        </Form>

        <div className="login-tips">
          <p>默认账号:</p>
          <p>管理员: admin / admin123</p>
          <p>分析师: analyst / admin123</p>
          <p>查看者: viewer / admin123</p>
        </div>
      </Card>
    </div>
  );
};

export default Login;
```

### 3.2 布局组件

#### components/Layout/index.tsx

```typescript
import React from 'react';
import { Layout as AntLayout, Menu } from 'antd';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import {
  DashboardOutlined,
  DatabaseOutlined,
  UserOutlined,
  SafetyOutlined,
} from '@ant-design/icons';
import Header from './Header';
import './index.css';

const { Sider, Content } = AntLayout;

const Layout: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const menuItems = [
    {
      key: '/dashboard',
      icon: <DashboardOutlined />,
      label: '报表管理',
      children: [
        { key: '/dashboard/list', label: '报表列表' },
      ],
    },
    {
      key: '/datasource',
      icon: <DatabaseOutlined />,
      label: '数据源管理',
      children: [
        { key: '/datasource/list', label: '数据源列表' },
        { key: '/datasource/create', label: '创建数据源' },
      ],
    },
    {
      key: '/user',
      icon: <UserOutlined />,
      label: '用户管理',
      children: [
        { key: '/user/list', label: '用户列表' },
        { key: '/user/role', label: '角色管理' },
      ],
    },
  ];

  return (
    <AntLayout style={{ minHeight: '100vh' }}>
      <Header />
      <AntLayout>
        <Sider width={200} theme="light">
          <Menu
            mode="inline"
            selectedKeys={[location.pathname]}
            defaultOpenKeys={['/dashboard', '/datasource', '/user']}
            items={menuItems}
            onClick={({ key }) => navigate(key)}
          />
        </Sider>
        <Content style={{ padding: '24px', background: '#f0f2f5' }}>
          <Outlet />
        </Content>
      </AntLayout>
    </AntLayout>
  );
};

export default Layout;
```

### 3.3 数据源列表页面

#### pages/DataSource/List.tsx

```typescript
import React, { useEffect, useState } from 'react';
import { Table, Button, Space, Tag, message, Popconfirm } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { datasourceApi } from '../../api/datasource';

const DataSourceList: React.FC = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState([]);

  const loadData = async () => {
    setLoading(true);
    try {
      const data = await datasourceApi.getList({});
      setDataSource(data.records || []);
    } catch (error) {
      message.error('加载数据源列表失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleDelete = async (id: number) => {
    try {
      await datasourceApi.delete(id);
      message.success('删除成功');
      loadData();
    } catch (error) {
      message.error('删除失败');
    }
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 80,
    },
    {
      title: '数据源名称',
      dataIndex: 'sourceName',
      key: 'sourceName',
    },
    {
      title: '类型',
      dataIndex: 'sourceType',
      key: 'sourceType',
      render: (type: string) => <Tag color="blue">{type}</Tag>,
    },
    {
      title: '主机',
      dataIndex: 'host',
      key: 'host',
    },
    {
      title: '端口',
      dataIndex: 'port',
      key: 'port',
      width: 80,
    },
    {
      title: '数据库',
      dataIndex: 'databaseName',
      key: 'databaseName',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: number) => (
        <Tag color={status === 1 ? 'green' : 'red'}>
          {status === 1 ? '启用' : '禁用'}
        </Tag>
      ),
    },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: any) => (
        <Space>
          <Button type="link" size="small">
            编辑
          </Button>
          <Button type="link" size="small">
            测试连接
          </Button>
          <Popconfirm
            title="确定要删除这个数据源吗？"
            onConfirm={() => handleDelete(record.id)}
          >
            <Button type="link" danger size="small">
              删除
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <div style={{ marginBottom: 16 }}>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => navigate('/datasource/create')}
        >
          创建数据源
        </Button>
      </div>
      <Table
        columns={columns}
        dataSource={dataSource}
        loading={loading}
        rowKey="id"
      />
    </div>
  );
};

export default DataSourceList;
```

---

## 四、配置文件

### 4.1 环境配置

#### .env.development

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_TITLE=DW Dashboard
```

#### .env.production

```env
VITE_API_BASE_URL=https://your-domain.com/api
VITE_APP_TITLE=DW Dashboard
```

### 4.2 Vite配置

#### vite.config.ts

```typescript
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
```

### 4.3 TypeScript配置

#### tsconfig.json

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "useDefineForClassFields": true,
    "lib": ["ES2020", "DOM", "DOM.Iterable"],
    "module": "ESNext",
    "skipLibCheck": true,
    "moduleResolution": "bundler",
    "allowImportingTsExtensions": true,
    "resolveJsonModule": true,
    "isolatedModules": true,
    "noEmit": true,
    "jsx": "react-jsx",
    "strict": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noFallthroughCasesInSwitch": true,
    "baseUrl": ".",
    "paths": {
      "@/*": ["src/*"]
    }
  },
  "include": ["src"],
  "references": [{ "path": "./tsconfig.node.json" }]
}
```

---

## 五、启动和构建

### 5.1 开发环境

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 访问地址
# http://localhost:3000
```

### 5.2 生产环境

```bash
# 构建生产版本
npm run build

# 预览生产版本
npm run preview
```

### 5.3 代码检查

```bash
# ESLint检查
npm run lint

# 格式化代码
npm run format
```

---

## 六、开发注意事项

1. **API调用**: 所有API调用都通过封装的request工具,自动处理Token和错误
2. **状态管理**: 使用Zustand管理全局状态,简单高效
3. **路由守卫**: 未登录用户自动跳转到登录页
4. **错误处理**: 统一的错误提示和处理机制
5. **代码规范**: 遵循TypeScript和React最佳实践
6. **组件复用**: 提取通用组件,提高代码复用率
7. **性能优化**: 使用React.memo、useMemo、useCallback等优化性能

---

## 七、常见问题

### 7.1 跨域问题

开发环境通过Vite代理解决跨域:

```typescript
// vite.config.ts
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
    },
  },
}
```

### 7.2 Token过期处理

Token过期自动跳转到登录页,在request.ts中已处理。

### 7.3 路由刷新404

使用BrowserRouter需要服务器配置,或使用HashRouter。

---

**完成前端实现后,即可与后端联调测试!**
