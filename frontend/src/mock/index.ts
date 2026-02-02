/**
 * Mock服务 - 拦截API请求并返回Mock数据
 */

import {
  mockUsers,
  mockRoles,
  mockDataSources,
  mockDashboards,
  mockPermissions,
  mockQueryResults,
} from './data'

// 模拟延迟
const delay = (ms: number = 300) => new Promise(resolve => setTimeout(resolve, ms))

// 生成响应格式
const success = (data: any, message: string = '操作成功') => ({
  code: 200,
  success: true,
  data,
  message,
})

const error = (message: string = '操作失败', code: number = 400) => ({
  code,
  success: false,
  data: null,
  message,
})

// 存储数据（模拟数据库）
let users = [...mockUsers]
let roles = [...mockRoles]
let dataSources = [...mockDataSources]
let dashboards = [...mockDashboards]
let permissions = [...mockPermissions]

// 当前用户
let currentUser: any = null

// Mock API处理器
export const mockHandlers = {
  // ==================== 认证相关 ====================
  '/auth/login': async (data: any) => {
    await delay()
    const { username, password } = data
    const user = users.find(u => u.username === username && u.password === password)

    if (user) {
      currentUser = user
      const token = `mock-token-${user.id}-${Date.now()}`
      return success({ token, user: { ...user, password: undefined } }, '登录成功')
    }
    return error('用户名或密码错误', 401)
  },

  '/auth/logout': async () => {
    await delay()
    currentUser = null
    return success(null, '退出成功')
  },

  '/auth/current': async () => {
    await delay()
    if (currentUser) {
      return success({ ...currentUser, password: undefined })
    }
    return error('未登录', 401)
  },

  // ==================== 数据源相关 ====================
  '/datasource/list': async (params: any) => {
    await delay()
    const { page = 1, pageSize = 10, name } = params

    let filtered = dataSources
    if (name) {
      filtered = filtered.filter(ds => ds.name.includes(name))
    }

    const start = (page - 1) * pageSize
    const end = start + pageSize
    const list = filtered.slice(start, end)

    return success({
      list,
      total: filtered.length,
      page,
      pageSize,
    })
  },

  '/datasource/all': async () => {
    await delay()
    return success(dataSources.filter(ds => ds.status === 1))
  },

  '/datasource/detail': async (id: number) => {
    await delay()
    const ds = dataSources.find(d => d.id === id)
    if (ds) {
      return success(ds)
    }
    return error('数据源不存在', 404)
  },

  '/datasource/create': async (data: any) => {
    await delay()
    const newDs = {
      ...data,
      id: Math.max(...dataSources.map(d => d.id)) + 1,
      status: 1,
      createdAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
      updatedAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
    }
    dataSources.push(newDs)
    return success(newDs, '创建成功')
  },

  '/datasource/update': async (id: number, data: any) => {
    await delay()
    const index = dataSources.findIndex(d => d.id === id)
    if (index !== -1) {
      dataSources[index] = {
        ...dataSources[index],
        ...data,
        updatedAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
      }
      return success(dataSources[index], '更新成功')
    }
    return error('数据源不存在', 404)
  },

  '/datasource/test': async () => {
    await delay(1000)
    // 模拟80%成功率
    if (Math.random() > 0.2) {
      return success(null, '连接测试成功')
    }
    return error('连接测试失败：无法连接到数据库')
  },

  '/datasource/status': async (id: number, status: number) => {
    await delay()
    const index = dataSources.findIndex(d => d.id === id)
    if (index !== -1) {
      dataSources[index].status = status
      dataSources[index].updatedAt = new Date().toISOString().slice(0, 19).replace('T', ' ')
      return success(null, '状态更新成功')
    }
    return error('数据源不存在', 404)
  },

  '/datasource/delete': async (id: number) => {
    await delay()
    const index = dataSources.findIndex(d => d.id === id)
    if (index !== -1) {
      dataSources.splice(index, 1)
      return success(null, '删除成功')
    }
    return error('数据源不存在', 404)
  },

  // ==================== 报表相关 ====================
  '/dashboard/list': async (params: any) => {
    await delay()
    const { page = 1, pageSize = 10, name, status } = params

    let filtered = dashboards
    if (name) {
      filtered = filtered.filter(d => d.name.includes(name))
    }
    if (status) {
      filtered = filtered.filter(d => d.status === status)
    }

    const start = (page - 1) * pageSize
    const end = start + pageSize
    const list = filtered.slice(start, end)

    return success({
      list,
      total: filtered.length,
      page,
      pageSize,
    })
  },

  '/dashboard/detail': async (id: number) => {
    await delay()
    const dashboard = dashboards.find(d => d.id === id)
    if (dashboard) {
      return success(dashboard)
    }
    return error('报表不存在', 404)
  },

  '/dashboard/create': async (data: any) => {
    await delay()
    const newDashboard = {
      ...data,
      id: Math.max(...dashboards.map(d => d.id)) + 1,
      status: 'draft',
      createdBy: currentUser?.username || 'admin',
      createdAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
      updatedAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
    }
    dashboards.push(newDashboard)
    return success(newDashboard, '创建成功')
  },

  '/dashboard/update': async (id: number, data: any) => {
    await delay()
    const index = dashboards.findIndex(d => d.id === id)
    if (index !== -1) {
      dashboards[index] = {
        ...dashboards[index],
        ...data,
        updatedAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
      }
      return success(dashboards[index], '更新成功')
    }
    return error('报表不存在', 404)
  },

  '/dashboard/publish': async (id: number) => {
    await delay()
    const index = dashboards.findIndex(d => d.id === id)
    if (index !== -1) {
      dashboards[index].status = 'published'
      dashboards[index].updatedAt = new Date().toISOString().slice(0, 19).replace('T', ' ')
      return success(null, '发布成功')
    }
    return error('报表不存在', 404)
  },

  '/dashboard/offline': async (id: number) => {
    await delay()
    const index = dashboards.findIndex(d => d.id === id)
    if (index !== -1) {
      dashboards[index].status = 'offline'
      dashboards[index].updatedAt = new Date().toISOString().slice(0, 19).replace('T', ' ')
      return success(null, '下线成功')
    }
    return error('报表不存在', 404)
  },

  '/dashboard/delete': async (id: number) => {
    await delay()
    const index = dashboards.findIndex(d => d.id === id)
    if (index !== -1) {
      dashboards.splice(index, 1)
      return success(null, '删除成功')
    }
    return error('报表不存在', 404)
  },

  '/dashboard/execute': async (data: any) => {
    await delay(800)
    const { dashboardId, parameters } = data
    const result = mockQueryResults[dashboardId as keyof typeof mockQueryResults]

    if (result) {
      return success(result, '查询成功')
    }
    return error('报表不存在或查询失败', 404)
  },

  // ==================== 用户相关 ====================
  '/user/list': async (params: any) => {
    await delay()
    const { page = 1, pageSize = 10, username } = params

    let filtered = users
    if (username) {
      filtered = filtered.filter(u => u.username.includes(username) || u.realName.includes(username))
    }

    const start = (page - 1) * pageSize
    const end = start + pageSize
    const list = filtered.slice(start, end).map(u => ({ ...u, password: undefined }))

    return success({
      list,
      total: filtered.length,
      page,
      pageSize,
    })
  },

  '/user/detail': async (id: number) => {
    await delay()
    const user = users.find(u => u.id === id)
    if (user) {
      return success({ ...user, password: undefined })
    }
    return error('用户不存在', 404)
  },

  '/user/create': async (data: any) => {
    await delay()
    const newUser = {
      ...data,
      id: Math.max(...users.map(u => u.id)) + 1,
      status: 1,
      createdAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
      updatedAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
    }
    users.push(newUser)
    return success({ ...newUser, password: undefined }, '创建成功')
  },

  '/user/update': async (id: number, data: any) => {
    await delay()
    const index = users.findIndex(u => u.id === id)
    if (index !== -1) {
      users[index] = {
        ...users[index],
        ...data,
        updatedAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
      }
      return success({ ...users[index], password: undefined }, '更新成功')
    }
    return error('用户不存在', 404)
  },

  '/user/status': async (id: number, status: number) => {
    await delay()
    const index = users.findIndex(u => u.id === id)
    if (index !== -1) {
      users[index].status = status
      users[index].updatedAt = new Date().toISOString().slice(0, 19).replace('T', ' ')
      return success(null, '状态更新成功')
    }
    return error('用户不存在', 404)
  },

  '/user/delete': async (id: number) => {
    await delay()
    const index = users.findIndex(u => u.id === id)
    if (index !== -1) {
      users.splice(index, 1)
      return success(null, '删除成功')
    }
    return error('用户不存在', 404)
  },

  // ==================== 角色相关 ====================
  '/role/list': async (params: any) => {
    await delay()
    const { page = 1, pageSize = 10 } = params

    const start = (page - 1) * pageSize
    const end = start + pageSize
    const list = roles.slice(start, end)

    return success({
      list,
      total: roles.length,
      page,
      pageSize,
    })
  },

  '/role/all': async () => {
    await delay()
    return success(roles)
  },

  // ==================== 权限相关 ====================
  '/permission/list': async (params: any) => {
    await delay()
    const { page = 1, pageSize = 10 } = params

    const start = (page - 1) * pageSize
    const end = start + pageSize
    const list = permissions.slice(start, end)

    return success({
      list,
      total: permissions.length,
      page,
      pageSize,
    })
  },

  '/permission/grant/user': async (data: any) => {
    await delay()
    const newPermission = {
      ...data,
      id: Math.max(...permissions.map(p => p.id)) + 1,
      targetType: 'user',
      createdAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
    }
    permissions.push(newPermission)
    return success(newPermission, '授权成功')
  },

  '/permission/grant/role': async (data: any) => {
    await delay()
    const newPermission = {
      ...data,
      id: Math.max(...permissions.map(p => p.id)) + 1,
      targetType: 'role',
      createdAt: new Date().toISOString().slice(0, 19).replace('T', ' '),
    }
    permissions.push(newPermission)
    return success(newPermission, '授权成功')
  },

  '/permission/revoke': async (id: number) => {
    await delay()
    const index = permissions.findIndex(p => p.id === id)
    if (index !== -1) {
      permissions.splice(index, 1)
      return success(null, '撤销成功')
    }
    return error('权限不存在', 404)
  },
}

// 启用Mock模式
export const enableMock = () => {
  console.log('🎭 Mock模式已启用')

  // 拦截fetch请求
  const originalFetch = window.fetch
  window.fetch = async (input: RequestInfo | URL, init?: RequestInit) => {
    const url = typeof input === 'string' ? input : input.toString()

    // 只拦截/api开头的请求
    if (url.startsWith('/api')) {
      const path = url.replace('/api', '')
      const method = init?.method || 'GET'

      console.log(`🎭 Mock拦截: ${method} ${path}`)

      try {
        let result
        const body = init?.body ? JSON.parse(init.body as string) : {}

        // 根据路径和方法调用对应的handler
        if (method === 'POST' && path === '/auth/login') {
          result = await mockHandlers['/auth/login'](body)
        } else if (method === 'POST' && path === '/auth/logout') {
          result = await mockHandlers['/auth/logout']()
        } else if (method === 'GET' && path === '/auth/current') {
          result = await mockHandlers['/auth/current']()
        } else if (method === 'GET' && path.startsWith('/datasource/list')) {
          const params = new URLSearchParams(url.split('?')[1])
          result = await mockHandlers['/datasource/list'](Object.fromEntries(params))
        } else if (method === 'GET' && path === '/datasource/all') {
          result = await mockHandlers['/datasource/all']()
        } else if (method === 'GET' && path.match(/\/datasource\/detail\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/datasource/detail'](id)
        } else if (method === 'POST' && path === '/datasource/create') {
          result = await mockHandlers['/datasource/create'](body)
        } else if (method === 'PUT' && path.match(/\/datasource\/update\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/datasource/update'](id, body)
        } else if (method === 'POST' && path === '/datasource/test') {
          result = await mockHandlers['/datasource/test']()
        } else if (method === 'PUT' && path.match(/\/datasource\/status\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/datasource/status'](id, body.status)
        } else if (method === 'DELETE' && path.match(/\/datasource\/delete\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/datasource/delete'](id)
        } else if (method === 'GET' && path.startsWith('/dashboard/list')) {
          const params = new URLSearchParams(url.split('?')[1])
          result = await mockHandlers['/dashboard/list'](Object.fromEntries(params))
        } else if (method === 'GET' && path.match(/\/dashboard\/detail\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/dashboard/detail'](id)
        } else if (method === 'POST' && path === '/dashboard/create') {
          result = await mockHandlers['/dashboard/create'](body)
        } else if (method === 'PUT' && path.match(/\/dashboard\/update\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/dashboard/update'](id, body)
        } else if (method === 'PUT' && path.match(/\/dashboard\/publish\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/dashboard/publish'](id)
        } else if (method === 'PUT' && path.match(/\/dashboard\/offline\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/dashboard/offline'](id)
        } else if (method === 'DELETE' && path.match(/\/dashboard\/delete\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/dashboard/delete'](id)
        } else if (method === 'POST' && path === '/dashboard/execute') {
          result = await mockHandlers['/dashboard/execute'](body)
        } else if (method === 'GET' && path.startsWith('/user/list')) {
          const params = new URLSearchParams(url.split('?')[1])
          result = await mockHandlers['/user/list'](Object.fromEntries(params))
        } else if (method === 'GET' && path.match(/\/user\/detail\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/user/detail'](id)
        } else if (method === 'POST' && path === '/user/create') {
          result = await mockHandlers['/user/create'](body)
        } else if (method === 'PUT' && path.match(/\/user\/update\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/user/update'](id, body)
        } else if (method === 'PUT' && path.match(/\/user\/status\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/user/status'](id, body.status)
        } else if (method === 'DELETE' && path.match(/\/user\/delete\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/user/delete'](id)
        } else if (method === 'GET' && path.startsWith('/role/list')) {
          const params = new URLSearchParams(url.split('?')[1])
          result = await mockHandlers['/role/list'](Object.fromEntries(params))
        } else if (method === 'GET' && path === '/role/all') {
          result = await mockHandlers['/role/all']()
        } else if (method === 'GET' && path.startsWith('/permission/list')) {
          const params = new URLSearchParams(url.split('?')[1])
          result = await mockHandlers['/permission/list'](Object.fromEntries(params))
        } else if (method === 'POST' && path === '/permission/grant/user') {
          result = await mockHandlers['/permission/grant/user'](body)
        } else if (method === 'POST' && path === '/permission/grant/role') {
          result = await mockHandlers['/permission/grant/role'](body)
        } else if (method === 'DELETE' && path.match(/\/permission\/revoke\/\d+/)) {
          const id = parseInt(path.split('/').pop()!)
          result = await mockHandlers['/permission/revoke'](id)
        } else {
          // 未匹配的请求，返回404
          result = error('接口不存在', 404)
        }

        return new Response(JSON.stringify(result), {
          status: 200,
          headers: { 'Content-Type': 'application/json' },
        })
      } catch (err) {
        console.error('Mock处理错误:', err)
        return new Response(JSON.stringify(error('Mock处理错误', 500)), {
          status: 500,
          headers: { 'Content-Type': 'application/json' },
        })
      }
    }

    // 非/api请求，使用原始fetch
    return originalFetch(input, init)
  }
}
