import { http } from '@/utils/request'
import type { Dashboard, PageParams, PageResult, QueryExecutionLog } from '@/types'

// 创建报表
export const createDashboard = (data: Partial<Dashboard>) => {
  return http.post<Dashboard>('/dashboard/create', data)
}

// 获取报表列表
export const getDashboardList = (params?: PageParams) => {
  return http.get<PageResult<Dashboard>>('/dashboard/list', { params })
}

// 获取报表详情
export const getDashboardDetail = (id: number) => {
  return http.get<Dashboard>(`/dashboard/detail/${id}`)
}

// 更新报表
export const updateDashboard = (id: number, data: Partial<Dashboard>) => {
  return http.put<Dashboard>(`/dashboard/update/${id}`, data)
}

// 发布报表
export const publishDashboard = (id: number) => {
  return http.put(`/dashboard/publish/${id}`)
}

// 下线报表
export const offlineDashboard = (id: number) => {
  return http.put(`/dashboard/offline/${id}`)
}

// 删除报表
export const deleteDashboard = (id: number) => {
  return http.delete(`/dashboard/delete/${id}`)
}

// 执行报表查询
export const executeDashboard = (data: { dashboardId: number; parameters?: Record<string, any> }) => {
  return http.post<any>('/dashboard/execute', data)
}

// 清除报表缓存
export const clearDashboardCache = (dashboardId: number) => {
  return http.delete(`/dashboard/cache/${dashboardId}`)
}

// 获取报表执行日志
export const getDashboardExecutionLogs = (params: PageParams & { dashboardId?: number }) => {
  return http.get<PageResult<QueryExecutionLog>>('/dashboard/execution-logs', { params })
}

// 获取报表分类列表
export const getDashboardCategories = () => {
  return http.get<string[]>('/dashboard/categories')
}

// 获取报表标签列表
export const getDashboardTags = () => {
  return http.get<string[]>('/dashboard/tags')
}
