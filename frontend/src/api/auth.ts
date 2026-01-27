import { http } from '@/utils/request'
import type { LoginParams, LoginResult, User } from '@/types'

// 登录
export const login = (data: LoginParams) => {
  return http.post<LoginResult>('/auth/login', data)
}

// 登出
export const logout = () => {
  return http.post('/auth/logout')
}

// 刷新Token
export const refreshToken = () => {
  return http.post<{ token: string }>('/auth/refresh')
}

// 获取当前用户信息
export const getCurrentUser = () => {
  return http.get<User>('/auth/current')
}

// 修改密码
export const changePassword = (data: { oldPassword: string; newPassword: string }) => {
  return http.post('/auth/change-password', data)
}
