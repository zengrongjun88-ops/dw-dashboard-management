// 通用响应类型
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
  success: boolean
}

// 分页请求参数
export interface PageParams {
  page: number
  pageSize: number
  [key: string]: any
}

// 分页响应数据
export interface PageResult<T = any> {
  list: T[]
  total: number
  page: number
  pageSize: number
}

// 用户类型
export interface User {
  id: number
  username: string
  realName: string
  email: string
  phone: string
  status: number
  roleIds: number[]
  roleNames: string[]
  createTime: string
  updateTime: string
}

// 角色类型
export interface Role {
  id: number
  roleName: string
  roleCode: string
  description: string
  status: number
  createTime: string
  updateTime: string
}

// 数据源类型
export interface DataSource {
  id: number
  name: string
  type: 'mysql' | 'postgresql' | 'oracle' | 'sqlserver' | 'clickhouse'
  host: string
  port: number
  database: string
  username: string
  password?: string
  status: number
  description: string
  createTime: string
  updateTime: string
}

// 报表定义类型
export interface Dashboard {
  id: number
  name: string
  description: string
  category: string
  tags: string[]
  status: 'draft' | 'published' | 'offline'
  isPublic: boolean
  datasourceId: number
  datasourceName: string
  queryConfig: QueryConfig
  displayConfig: DisplayConfig
  createUserId: number
  createUserName: string
  createTime: string
  updateTime: string
}

// 查询配置类型
export interface QueryConfig {
  sql: string
  parameters: QueryParameter[]
  enableCache: boolean
  cacheExpireSeconds: number
  enablePagination: boolean
  timeout: number
}

// 查询参数类型
export interface QueryParameter {
  name: string
  label: string
  type: 'string' | 'number' | 'date' | 'datetime' | 'select'
  required: boolean
  defaultValue?: any
  options?: Array<{ label: string; value: any }>
}

// 展示配置类型
export interface DisplayConfig {
  chartType: 'table' | 'line' | 'bar' | 'pie' | 'scatter' | 'gauge' | 'card'
  columns: ColumnConfig[]
  filters: FilterConfig[]
  chartOptions?: any
}

// 列配置类型
export interface ColumnConfig {
  field: string
  title: string
  width?: number
  align?: 'left' | 'center' | 'right'
  fixed?: 'left' | 'right'
  sortable?: boolean
  filterable?: boolean
  formatter?: string
}

// 筛选器配置类型
export interface FilterConfig {
  field: string
  label: string
  type: 'input' | 'select' | 'date' | 'dateRange'
  options?: Array<{ label: string; value: any }>
}

// 报表权限类型
export interface DashboardPermission {
  id: number
  dashboardId: number
  targetType: 'user' | 'role'
  targetId: number
  targetName: string
  permissionLevel: 'view' | 'edit' | 'manage'
  createTime: string
}

// 查询执行日志类型
export interface QueryExecutionLog {
  id: number
  dashboardId: number
  dashboardName: string
  executeSql: string
  executeParams: string
  executeUserId: number
  executeUserName: string
  status: 'success' | 'failed'
  resultRows: number
  executionTime: number
  errorMessage: string
  createTime: string
}

// 登录请求参数
export interface LoginParams {
  username: string
  password: string
}

// 登录响应数据
export interface LoginResult {
  token: string
  user: User
}
