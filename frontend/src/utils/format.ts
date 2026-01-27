import dayjs from 'dayjs'

// 日期格式化
export const formatDate = (date: string | number | Date, format = 'YYYY-MM-DD'): string => {
  if (!date) return '-'
  return dayjs(date).format(format)
}

export const formatDateTime = (date: string | number | Date): string => {
  return formatDate(date, 'YYYY-MM-DD HH:mm:ss')
}

export const formatTime = (date: string | number | Date): string => {
  return formatDate(date, 'HH:mm:ss')
}

// 相对时间
export const formatRelativeTime = (date: string | number | Date): string => {
  if (!date) return '-'
  const now = dayjs()
  const target = dayjs(date)
  const diff = now.diff(target, 'second')

  if (diff < 60) return '刚刚'
  if (diff < 3600) return `${Math.floor(diff / 60)}分钟前`
  if (diff < 86400) return `${Math.floor(diff / 3600)}小时前`
  if (diff < 2592000) return `${Math.floor(diff / 86400)}天前`
  return formatDate(date)
}

// 数字格式化
export const formatNumber = (num: number, decimals = 2): string => {
  if (num === null || num === undefined) return '-'
  return num.toFixed(decimals)
}

export const formatThousands = (num: number): string => {
  if (num === null || num === undefined) return '-'
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
}

export const formatPercent = (num: number, decimals = 2): string => {
  if (num === null || num === undefined) return '-'
  return `${(num * 100).toFixed(decimals)}%`
}

// 文件大小格式化
export const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return `${(bytes / Math.pow(k, i)).toFixed(2)} ${sizes[i]}`
}

// 时长格式化
export const formatDuration = (ms: number): string => {
  if (ms < 1000) return `${ms}ms`
  if (ms < 60000) return `${(ms / 1000).toFixed(2)}s`
  if (ms < 3600000) return `${Math.floor(ms / 60000)}m ${Math.floor((ms % 60000) / 1000)}s`
  return `${Math.floor(ms / 3600000)}h ${Math.floor((ms % 3600000) / 60000)}m`
}

// 状态格式化
export const formatStatus = (status: string | number): string => {
  const statusMap: Record<string | number, string> = {
    0: '禁用',
    1: '启用',
    draft: '草稿',
    published: '已发布',
    offline: '已下线',
    active: '活跃',
    inactive: '不活跃',
  }
  return statusMap[status] || String(status)
}

// 权限级别格式化
export const formatPermissionLevel = (level: string): string => {
  const levelMap: Record<string, string> = {
    view: '查看',
    edit: '编辑',
    manage: '管理',
  }
  return levelMap[level] || level
}

// 数据源类型格式化
export const formatDataSourceType = (type: string): string => {
  const typeMap: Record<string, string> = {
    mysql: 'MySQL',
    postgresql: 'PostgreSQL',
    oracle: 'Oracle',
    sqlserver: 'SQL Server',
    clickhouse: 'ClickHouse',
  }
  return typeMap[type] || type
}

// 图表类型格式化
export const formatChartType = (type: string): string => {
  const typeMap: Record<string, string> = {
    table: '表格',
    line: '折线图',
    bar: '柱状图',
    pie: '饼图',
    scatter: '散点图',
    gauge: '仪表盘',
    card: '卡片',
  }
  return typeMap[type] || type
}

// 脱敏处理
export const maskPhone = (phone: string): string => {
  if (!phone) return '-'
  return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
}

export const maskEmail = (email: string): string => {
  if (!email) return '-'
  return email.replace(/(.{2}).*(@.*)/, '$1***$2')
}

export const maskIdCard = (idCard: string): string => {
  if (!idCard) return '-'
  return idCard.replace(/(\d{6})\d{8}(\d{4})/, '$1********$2')
}
