import { http } from '@/utils/request'
import type { DataSource, PageParams, PageResult } from '@/types'

// 创建数据源
export const createDataSource = (data: Partial<DataSource>) => {
  return http.post<DataSource>('/datasource/create', data)
}

// 获取数据源列表
export const getDataSourceList = (params?: PageParams) => {
  return http.get<PageResult<DataSource>>('/datasource/list', { params })
}

// 获取所有启用的数据源
export const getAllDataSources = () => {
  return http.get<DataSource[]>('/datasource/all')
}

// 获取数据源详情
export const getDataSourceDetail = (id: number) => {
  return http.get<DataSource>(`/datasource/detail/${id}`)
}

// 更新数据源
export const updateDataSource = (id: number, data: Partial<DataSource>) => {
  return http.put<DataSource>(`/datasource/update/${id}`, data)
}

// 测试数据源连接
export const testDataSourceConnection = (data: Partial<DataSource>) => {
  return http.post<{ success: boolean; message: string }>('/datasource/test', data)
}

// 修改数据源状态
export const updateDataSourceStatus = (id: number, status: number) => {
  return http.put(`/datasource/status/${id}`, { status })
}

// 删除数据源
export const deleteDataSource = (id: number) => {
  return http.delete(`/datasource/delete/${id}`)
}
