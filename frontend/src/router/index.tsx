import { lazy, Suspense } from 'react'
import { createBrowserRouter, Navigate } from 'react-router-dom'
import { Spin } from 'antd'
import { useAuthStore } from '@/store/auth'
import MainLayout from '@/components/Layout/MainLayout'

// 懒加载组件
const Login = lazy(() => import('@/pages/Login'))
const DashboardList = lazy(() => import('@/pages/Dashboard/List'))
const DashboardDetail = lazy(() => import('@/pages/Dashboard/Detail'))
const DashboardView = lazy(() => import('@/pages/Dashboard/View'))
const Designer = lazy(() => import('@/pages/Designer'))
const DataSourceList = lazy(() => import('@/pages/DataSource/List'))
const DataSourceCreate = lazy(() => import('@/pages/DataSource/Create'))
const UserList = lazy(() => import('@/pages/User/List'))
const UserRoleManage = lazy(() => import('@/pages/User/RoleManage'))
const PermissionManage = lazy(() => import('@/pages/Permission'))

// Loading组件
const PageLoading = () => (
  <div style={{ textAlign: 'center', padding: '100px 0' }}>
    <Spin size="large" />
  </div>
)

// 路由守卫 - 需要认证
const AuthGuard = ({ children }: { children: React.ReactNode }) => {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated)

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }

  return <>{children}</>
}

// 路由守卫 - 已登录用户访问登录页
const LoginGuard = ({ children }: { children: React.ReactNode }) => {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated)

  if (isAuthenticated) {
    return <Navigate to="/dashboard" replace />
  }

  return <>{children}</>
}

// 路由配置
export const router = createBrowserRouter([
  {
    path: '/login',
    element: (
      <LoginGuard>
        <Suspense fallback={<PageLoading />}>
          <Login />
        </Suspense>
      </LoginGuard>
    ),
  },
  {
    path: '/',
    element: (
      <AuthGuard>
        <MainLayout />
      </AuthGuard>
    ),
    children: [
      {
        index: true,
        element: <Navigate to="/dashboard" replace />,
      },
      {
        path: 'dashboard',
        children: [
          {
            index: true,
            element: (
              <Suspense fallback={<PageLoading />}>
                <DashboardList />
              </Suspense>
            ),
          },
          {
            path: 'detail/:id',
            element: (
              <Suspense fallback={<PageLoading />}>
                <DashboardDetail />
              </Suspense>
            ),
          },
          {
            path: 'view/:id',
            element: (
              <Suspense fallback={<PageLoading />}>
                <DashboardView />
              </Suspense>
            ),
          },
        ],
      },
      {
        path: 'designer',
        children: [
          {
            index: true,
            element: (
              <Suspense fallback={<PageLoading />}>
                <Designer />
              </Suspense>
            ),
          },
          {
            path: ':id',
            element: (
              <Suspense fallback={<PageLoading />}>
                <Designer />
              </Suspense>
            ),
          },
        ],
      },
      {
        path: 'datasource',
        children: [
          {
            index: true,
            element: (
              <Suspense fallback={<PageLoading />}>
                <DataSourceList />
              </Suspense>
            ),
          },
          {
            path: 'create',
            element: (
              <Suspense fallback={<PageLoading />}>
                <DataSourceCreate />
              </Suspense>
            ),
          },
          {
            path: 'edit/:id',
            element: (
              <Suspense fallback={<PageLoading />}>
                <DataSourceCreate />
              </Suspense>
            ),
          },
        ],
      },
      {
        path: 'user',
        children: [
          {
            index: true,
            element: (
              <Suspense fallback={<PageLoading />}>
                <UserList />
              </Suspense>
            ),
          },
          {
            path: 'role',
            element: (
              <Suspense fallback={<PageLoading />}>
                <UserRoleManage />
              </Suspense>
            ),
          },
        ],
      },
      {
        path: 'permission',
        element: (
          <Suspense fallback={<PageLoading />}>
            <PermissionManage />
          </Suspense>
        ),
      },
    ],
  },
  {
    path: '*',
    element: <Navigate to="/dashboard" replace />,
  },
])
