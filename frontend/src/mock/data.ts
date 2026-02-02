/**
 * Mock数据 - 用于功能验证
 */

// 用户数据
export const mockUsers = [
  {
    id: 1,
    username: 'admin',
    password: '123456',
    realName: '系统管理员',
    email: 'admin@example.com',
    phone: '13800138000',
    status: 1,
    roleIds: [1],
    roleNames: ['管理员'],
    createdAt: '2026-01-01 10:00:00',
    updatedAt: '2026-01-01 10:00:00',
  },
  {
    id: 2,
    username: 'analyst',
    password: '123456',
    realName: '数据分析师',
    email: 'analyst@example.com',
    phone: '13800138001',
    status: 1,
    roleIds: [2],
    roleNames: ['分析师'],
    createdAt: '2026-01-02 10:00:00',
    updatedAt: '2026-01-02 10:00:00',
  },
  {
    id: 3,
    username: 'viewer',
    password: '123456',
    realName: '报表查看者',
    email: 'viewer@example.com',
    phone: '13800138002',
    status: 1,
    roleIds: [3],
    roleNames: ['查看者'],
    createdAt: '2026-01-03 10:00:00',
    updatedAt: '2026-01-03 10:00:00',
  },
]

// 角色数据
export const mockRoles = [
  {
    id: 1,
    roleName: '管理员',
    roleCode: 'ADMIN',
    description: '系统管理员，拥有所有权限',
    status: 1,
    createdAt: '2026-01-01 10:00:00',
  },
  {
    id: 2,
    roleName: '分析师',
    roleCode: 'ANALYST',
    description: '数据分析师，可以创建和编辑报表',
    status: 1,
    createdAt: '2026-01-01 10:00:00',
  },
  {
    id: 3,
    roleName: '查看者',
    roleCode: 'VIEWER',
    description: '报表查看者，只能查看报表',
    status: 1,
    createdAt: '2026-01-01 10:00:00',
  },
]

// 数据源数据
export const mockDataSources = [
  {
    id: 1,
    name: 'MySQL测试库',
    type: 'mysql',
    host: 'localhost',
    port: 3306,
    database: 'test_db',
    username: 'root',
    password: '******',
    status: 1,
    createdAt: '2026-01-01 10:00:00',
    updatedAt: '2026-01-01 10:00:00',
  },
  {
    id: 2,
    name: 'PostgreSQL生产库',
    type: 'postgresql',
    host: '192.168.1.100',
    port: 5432,
    database: 'prod_db',
    username: 'postgres',
    password: '******',
    status: 1,
    createdAt: '2026-01-02 10:00:00',
    updatedAt: '2026-01-02 10:00:00',
  },
  {
    id: 3,
    name: 'ClickHouse数据仓库',
    type: 'clickhouse',
    host: '192.168.1.200',
    port: 8123,
    database: 'dw',
    username: 'default',
    password: '******',
    status: 1,
    createdAt: '2026-01-03 10:00:00',
    updatedAt: '2026-01-03 10:00:00',
  },
]

// 报表数据
export const mockDashboards = [
  {
    id: 1,
    name: '销售数据报表',
    description: '展示每日销售数据趋势',
    category: '销售分析',
    tags: ['销售', '趋势'],
    status: 'published',
    isPublic: true,
    datasourceId: 1,
    datasourceName: 'MySQL测试库',
    createdBy: 'admin',
    createdAt: '2026-01-10 10:00:00',
    updatedAt: '2026-01-10 10:00:00',
    queryConfig: {
      sql: 'SELECT date, sales_amount, order_count FROM daily_sales WHERE date >= :startDate AND date <= :endDate ORDER BY date',
      parameters: [
        { name: 'startDate', label: '开始日期', type: 'date', required: true, defaultValue: '2026-01-01' },
        { name: 'endDate', label: '结束日期', type: 'date', required: true, defaultValue: '2026-01-30' },
      ],
      enableCache: true,
      cacheExpireSeconds: 300,
      enablePagination: false,
      timeout: 30,
    },
    displayConfig: {
      chartType: 'line',
      columns: [
        { field: 'date', title: '日期', width: 120 },
        { field: 'sales_amount', title: '销售额', width: 120 },
        { field: 'order_count', title: '订单数', width: 120 },
      ],
      filters: [],
      chartOptions: {
        title: { text: '销售趋势图' },
        xAxis: { type: 'category', data: [] },
        yAxis: { type: 'value' },
        series: [{ type: 'line', data: [] }],
      },
    },
  },
  {
    id: 2,
    name: '用户分析报表',
    description: '用户注册和活跃度分析',
    category: '用户分析',
    tags: ['用户', '活跃度'],
    status: 'published',
    isPublic: true,
    datasourceId: 1,
    datasourceName: 'MySQL测试库',
    createdBy: 'analyst',
    createdAt: '2026-01-11 10:00:00',
    updatedAt: '2026-01-11 10:00:00',
    queryConfig: {
      sql: 'SELECT user_type, COUNT(*) as count FROM users GROUP BY user_type',
      parameters: [],
      enableCache: true,
      cacheExpireSeconds: 600,
      enablePagination: false,
      timeout: 30,
    },
    displayConfig: {
      chartType: 'pie',
      columns: [
        { field: 'user_type', title: '用户类型', width: 120 },
        { field: 'count', title: '数量', width: 120 },
      ],
      filters: [],
      chartOptions: {
        title: { text: '用户类型分布' },
        series: [{ type: 'pie', data: [] }],
      },
    },
  },
  {
    id: 3,
    name: '订单统计报表',
    description: '订单状态统计',
    category: '订单分析',
    tags: ['订单', '统计'],
    status: 'draft',
    isPublic: false,
    datasourceId: 2,
    datasourceName: 'PostgreSQL生产库',
    createdBy: 'analyst',
    createdAt: '2026-01-12 10:00:00',
    updatedAt: '2026-01-12 10:00:00',
    queryConfig: {
      sql: 'SELECT status, COUNT(*) as count, SUM(amount) as total_amount FROM orders WHERE created_at >= :startDate GROUP BY status',
      parameters: [
        { name: 'startDate', label: '开始日期', type: 'date', required: true, defaultValue: '2026-01-01' },
      ],
      enableCache: false,
      cacheExpireSeconds: 0,
      enablePagination: true,
      timeout: 30,
    },
    displayConfig: {
      chartType: 'bar',
      columns: [
        { field: 'status', title: '订单状态', width: 120 },
        { field: 'count', title: '订单数', width: 120 },
        { field: 'total_amount', title: '总金额', width: 120 },
      ],
      filters: [],
      chartOptions: {
        title: { text: '订单状态统计' },
        xAxis: { type: 'category', data: [] },
        yAxis: { type: 'value' },
        series: [{ type: 'bar', data: [] }],
      },
    },
  },
]

// 权限数据
export const mockPermissions = [
  {
    id: 1,
    dashboardId: 1,
    dashboardName: '销售数据报表',
    targetType: 'user',
    targetId: 2,
    targetName: 'analyst',
    permissionLevel: 'edit',
    createdAt: '2026-01-10 10:00:00',
  },
  {
    id: 2,
    dashboardId: 1,
    dashboardName: '销售数据报表',
    targetType: 'role',
    targetId: 3,
    targetName: '查看者',
    permissionLevel: 'view',
    createdAt: '2026-01-10 10:00:00',
  },
  {
    id: 3,
    dashboardId: 2,
    dashboardName: '用户分析报表',
    targetType: 'user',
    targetId: 3,
    targetName: 'viewer',
    permissionLevel: 'view',
    createdAt: '2026-01-11 10:00:00',
  },
]

// Mock查询结果数据
export const mockQueryResults = {
  1: {
    // 销售数据报表
    columns: ['date', 'sales_amount', 'order_count'],
    data: [
      { date: '2026-01-01', sales_amount: 12500, order_count: 45 },
      { date: '2026-01-02', sales_amount: 15800, order_count: 52 },
      { date: '2026-01-03', sales_amount: 13200, order_count: 48 },
      { date: '2026-01-04', sales_amount: 16500, order_count: 58 },
      { date: '2026-01-05', sales_amount: 14300, order_count: 51 },
      { date: '2026-01-06', sales_amount: 18900, order_count: 65 },
      { date: '2026-01-07', sales_amount: 17600, order_count: 62 },
    ],
    total: 7,
  },
  2: {
    // 用户分析报表
    columns: ['user_type', 'count'],
    data: [
      { user_type: '普通用户', count: 1250 },
      { user_type: 'VIP用户', count: 380 },
      { user_type: '企业用户', count: 125 },
    ],
    total: 3,
  },
  3: {
    // 订单统计报表
    columns: ['status', 'count', 'total_amount'],
    data: [
      { status: '待支付', count: 156, total_amount: 45600 },
      { status: '已支付', count: 892, total_amount: 256800 },
      { status: '已发货', count: 745, total_amount: 198500 },
      { status: '已完成', count: 1523, total_amount: 456700 },
      { status: '已取消', count: 89, total_amount: 23400 },
    ],
    total: 5,
  },
}
