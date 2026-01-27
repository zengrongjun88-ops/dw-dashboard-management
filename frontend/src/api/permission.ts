import { http } from '@/utils/request'
import type { DashboardPermission, PageParams, PageResult } from '@/types'

// 检查用户权限
export const checkPermission = (params: { dashboardId: number; userId?: number }) => {
  return http.get<{ hasPermission: boolean; permissionLevel: string }>('/permission/check', { params })
}

// 获取报表权限列表
export const getPermissionList = (params: PageParams & { dashboardId?: number }) => {
  return http.get<PageResult<DashboardPermission>>('/permission/list', { params })
}

// 授予用户权限
export const grantUserPermission = (data: {
  dashboardId: number
  userId: number
  permissionLevel: string
}) => {
  return http.post('/permission/grant/user', data)
}

// 授予角色权限
export const grantRolePermission = (data: {
  dashboardId: number
  roleId: number
  permissionLevel: string
}) => {
  return http.post('/permission/grant/role', data)
}

// 撤销权限
export const revokePermission = (id: number) => {
  return http.delete(`/permission/revoke/${id}`)
}

// 批量授予权限
export const batchGrantPermission = (data: {
  dashboardId: number
  targetType: 'user' | 'role'
  targetIds: number[]
  permissionLevel: string
}) => {
  return http.post('/permission/batch-grant', data)
}
