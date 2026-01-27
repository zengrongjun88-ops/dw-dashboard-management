import { http } from '@/utils/request'
import type { User, Role, PageParams, PageResult } from '@/types'

// 创建用户
export const createUser = (data: Partial<User>) => {
  return http.post<User>('/user/create', data)
}

// 获取用户列表
export const getUserList = (params: PageParams) => {
  return http.get<PageResult<User>>('/user/list', { params })
}

// 获取用户详情
export const getUserDetail = (id: number) => {
  return http.get<User>(`/user/detail/${id}`)
}

// 更新用户
export const updateUser = (id: number, data: Partial<User>) => {
  return http.put<User>(`/user/update/${id}`, data)
}

// 修改用户状态
export const updateUserStatus = (id: number, status: number) => {
  return http.put(`/user/status/${id}`, { status })
}

// 删除用户
export const deleteUser = (id: number) => {
  return http.delete(`/user/delete/${id}`)
}

// 获取角色列表
export const getRoleList = (params?: PageParams) => {
  return http.get<PageResult<Role>>('/role/list', { params })
}

// 获取所有角色
export const getAllRoles = () => {
  return http.get<Role[]>('/role/all')
}

// 分配用户角色
export const assignUserRoles = (userId: number, roleIds: number[]) => {
  return http.post('/user/assign-roles', { userId, roleIds })
}
