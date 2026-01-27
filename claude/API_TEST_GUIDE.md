# API测试和验证指南

## 📋 测试概述

本文档提供了完整的API测试方案，包括手动测试和自动化测试。

---

## 🚀 快速测试

### 方式1: 使用HTML测试页面（推荐）

1. **启动后端服务**
```bash
# 确保MySQL和Redis已启动
mvn spring-boot:run
```

2. **打开测试页面**
```bash
# 在浏览器中打开
open test-api.html
# 或直接访问
file:///Users/zengrongjun/claudespace/dw-dashboard-management/test-api.html
```

3. **执行测试**
   - 点击"登录"按钮测试登录功能
   - 点击"运行所有测试"执行完整测试

### 方式2: 使用Knife4j API文档

1. **访问API文档**
```
http://localhost:8080/api/doc.html
```

2. **测试步骤**
   - 先调用登录接口获取Token
   - 点击"授权"按钮，输入Token
   - 测试其他接口

### 方式3: 使用curl命令

```bash
# 1. 登录获取Token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# 2. 使用Token访问接口
TOKEN="your_token_here"
curl -X GET http://localhost:8080/api/user/list \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📝 详细测试用例

### 1. 认证授权测试

#### 1.1 用户登录
**接口**: POST /api/auth/login

**测试用例1: 管理员登录**
```json
{
  "username": "admin",
  "password": "admin123"
}
```
**预期结果**:
- 返回code=200
- 返回token和用户信息
- 用户角色包含ROLE_ADMIN

**测试用例2: 分析师登录**
```json
{
  "username": "analyst",
  "password": "admin123"
}
```
**预期结果**:
- 返回code=200
- 用户角色包含ROLE_ANALYST

**测试用例3: 错误密码**
```json
{
  "username": "admin",
  "password": "wrong_password"
}
```
**预期结果**:
- 返回code=401
- 返回错误信息

#### 1.2 获取当前用户信息
**接口**: GET /api/auth/current

**测试步骤**:
1. 先登录获取Token
2. 携带Token访问接口

**预期结果**:
- 返回当前登录用户的详细信息
- 包含用户名、真实姓名、邮箱、角色等

#### 1.3 Token刷新
**接口**: POST /api/auth/refresh

**测试数据**:
```json
{
  "refreshToken": "your_refresh_token"
}
```

**预期结果**:
- 返回新的Token

#### 1.4 用户登出
**接口**: POST /api/auth/logout

**预期结果**:
- 返回code=200
- Token失效

---

### 2. 数据源管理测试

#### 2.1 获取数据源列表
**接口**: GET /api/datasource/list

**预期结果**:
- 返回数据源列表
- 包含示例数据源（本地MySQL、测试PostgreSQL）

#### 2.2 创建数据源
**接口**: POST /api/datasource/create

**测试数据**:
```json
{
  "sourceName": "测试MySQL数据源",
  "sourceType": "MYSQL",
  "host": "localhost",
  "port": 3306,
  "databaseName": "test_db",
  "username": "root",
  "password": "password",
  "description": "测试用MySQL数据源"
}
```

**预期结果**:
- 返回code=200
- 返回创建的数据源ID
- 密码已加密存储

#### 2.3 测试数据源连接
**接口**: POST /api/datasource/test

**测试数据**:
```json
{
  "sourceType": "MYSQL",
  "host": "localhost",
  "port": 3306,
  "databaseName": "dw_dashboard",
  "username": "root",
  "password": "your_password"
}
```

**预期结果**:
- 连接成功返回code=200
- 连接失败返回错误信息

#### 2.4 获取数据源详情
**接口**: GET /api/datasource/detail/{id}

**预期结果**:
- 返回数据源详细信息
- 密码字段已脱敏

#### 2.5 更新数据源状态
**接口**: PUT /api/datasource/status/{id}

**测试数据**:
```json
{
  "status": 0
}
```

**预期结果**:
- 数据源状态更新成功

#### 2.6 删除数据源
**接口**: DELETE /api/datasource/delete/{id}

**预期结果**:
- 数据源删除成功（逻辑删除）

---

### 3. 报表管理测试

#### 3.1 获取报表列表
**接口**: GET /api/dashboard/list

**预期结果**:
- 返回报表列表
- 包含示例报表

#### 3.2 创建报表
**接口**: POST /api/dashboard/create

**测试数据**:
```json
{
  "dashboardName": "用户分析报表",
  "dashboardCode": "USER_ANALYSIS",
  "category": "用户分析",
  "description": "用户数据分析报表"
}
```

**预期结果**:
- 返回code=200
- 返回创建的报表ID

#### 3.3 获取报表详情
**接口**: GET /api/dashboard/detail/{id}

**预期结果**:
- 返回报表详细信息
- 包含报表配置

#### 3.4 发布报表
**接口**: PUT /api/dashboard/publish/{id}

**预期结果**:
- 报表状态变为"已发布"

#### 3.5 下线报表
**接口**: PUT /api/dashboard/offline/{id}

**预期结果**:
- 报表状态变为"已下线"

#### 3.6 删除报表
**接口**: DELETE /api/dashboard/delete/{id}

**预期结果**:
- 报表删除成功（逻辑删除）

#### 3.7 执行报表查询
**接口**: POST /api/dashboard/execute

**测试数据**:
```json
{
  "dashboardId": 1,
  "queryId": 1,
  "params": {}
}
```

**预期结果**:
- 返回查询结果
- 包含数据行和列信息

#### 3.8 清除报表缓存
**接口**: DELETE /api/dashboard/cache/{dashboardId}

**预期结果**:
- 缓存清除成功

---

### 4. 用户管理测试

#### 4.1 获取用户列表
**接口**: GET /api/user/list

**预期结果**:
- 返回用户列表
- 包含admin、analyst、viewer三个用户

#### 4.2 创建用户
**接口**: POST /api/user/create

**测试数据**:
```json
{
  "username": "testuser",
  "password": "Test123456",
  "realName": "测试用户",
  "email": "test@example.com",
  "phone": "13800138000"
}
```

**预期结果**:
- 返回code=200
- 密码已加密存储

#### 4.3 获取用户详情
**接口**: GET /api/user/detail/{id}

**预期结果**:
- 返回用户详细信息
- 不包含密码字段

#### 4.4 更新用户状态
**接口**: PUT /api/user/status/{id}

**测试数据**:
```json
{
  "status": 0
}
```

**预期结果**:
- 用户状态更新成功

#### 4.5 删除用户
**接口**: DELETE /api/user/delete/{id}

**预期结果**:
- 用户删除成功（逻辑删除）

---

### 5. 角色管理测试

#### 5.1 获取角色列表
**接口**: GET /api/role/list

**预期结果**:
- 返回角色列表
- 包含ROLE_ADMIN、ROLE_ANALYST、ROLE_VIEWER

#### 5.2 获取所有角色
**接口**: GET /api/role/all

**预期结果**:
- 返回所有角色（不分页）

---

### 6. 权限管理测试

#### 6.1 检查用户权限
**接口**: GET /api/permission/check

**查询参数**:
- userId: 用户ID
- dashboardId: 报表ID

**预期结果**:
- 返回用户是否有权限访问该报表

#### 6.2 授予用户权限
**接口**: POST /api/permission/grant/user

**测试数据**:
```json
{
  "dashboardId": 1,
  "userId": 2,
  "permissionLevel": "VIEW"
}
```

**预期结果**:
- 权限授予成功

#### 6.3 授予角色权限
**接口**: POST /api/permission/grant/role

**测试数据**:
```json
{
  "dashboardId": 1,
  "roleId": 2,
  "permissionLevel": "EDIT"
}
```

**预期结果**:
- 权限授予成功

---

## 🔍 安全测试

### 1. SQL注入测试

**测试场景**: 尝试在查询参数中注入SQL

**测试数据**:
```json
{
  "sql": "SELECT * FROM users WHERE id = 1; DROP TABLE users;--"
}
```

**预期结果**:
- 请求被拦截
- 返回"SQL安全检查失败"错误

### 2. 未授权访问测试

**测试场景**: 不携带Token访问需要认证的接口

**预期结果**:
- 返回401 Unauthorized
- 提示"未授权"

### 3. 权限测试

**测试场景**: 使用viewer账号尝试创建数据源

**预期结果**:
- 返回403 Forbidden
- 提示"没有权限"

---

## 📊 性能测试

### 1. 并发测试

使用Apache Bench进行并发测试:

```bash
# 100个并发请求，总共1000个请求
ab -n 1000 -c 100 -H "Authorization: Bearer YOUR_TOKEN" \
  http://localhost:8080/api/user/list
```

**预期结果**:
- 响应时间 < 3秒
- 成功率 > 99%

### 2. 缓存测试

**测试步骤**:
1. 第一次执行查询，记录响应时间
2. 第二次执行相同查询，记录响应时间
3. 对比两次响应时间

**预期结果**:
- 第二次查询明显快于第一次
- 缓存命中率 > 60%

---

## ✅ 验收标准

### 功能验收
- [ ] 用户可以成功登录系统
- [ ] 用户可以创建和管理数据源
- [ ] 用户可以测试数据源连接
- [ ] 用户可以创建和管理报表
- [ ] 用户可以执行报表查询
- [ ] 用户可以导出报表数据
- [ ] 管理员可以管理用户和角色
- [ ] 管理员可以分配权限
- [ ] 系统正确控制权限访问

### 性能验收
- [ ] 报表查询响应时间 < 3秒
- [ ] 页面加载时间 < 2秒
- [ ] 支持100+并发用户
- [ ] 缓存命中率 > 60%

### 安全验收
- [ ] JWT Token正常工作
- [ ] SQL注入防护有效
- [ ] 敏感数据加密存储
- [ ] 权限控制正常工作
- [ ] 操作日志正常记录

---

## 🐛 常见问题排查

### 1. 登录失败

**问题**: 返回401错误

**排查步骤**:
1. 检查用户名密码是否正确
2. 检查数据库是否初始化成功
3. 查看后端日志

### 2. Token验证失败

**问题**: 返回401 Unauthorized

**排查步骤**:
1. 检查Token是否过期
2. 检查Token格式是否正确
3. 检查JWT配置

### 3. 数据源连接失败

**问题**: 测试连接返回错误

**排查步骤**:
1. 检查数据库是否启动
2. 检查连接参数是否正确
3. 检查网络连接

### 4. 权限不足

**问题**: 返回403 Forbidden

**排查步骤**:
1. 检查用户角色
2. 检查权限配置
3. 查看权限日志

---

## 📈 测试报告模板

```markdown
# API测试报告

## 测试信息
- 测试日期: YYYY-MM-DD
- 测试人员: XXX
- 测试环境: 开发环境
- 后端版本: 1.0.0

## 测试结果汇总
- 总测试用例数: XX
- 通过数: XX
- 失败数: XX
- 通过率: XX%

## 详细测试结果

### 1. 认证授权模块
- [✓] 用户登录
- [✓] 获取当前用户
- [✓] Token刷新
- [✓] 用户登出

### 2. 数据源管理模块
- [✓] 获取数据源列表
- [✓] 创建数据源
- [✓] 测试连接
- [✓] 更新状态
- [✓] 删除数据源

### 3. 报表管理模块
- [✓] 获取报表列表
- [✓] 创建报表
- [✓] 发布报表
- [✓] 执行查询
- [✓] 导出数据

### 4. 用户管理模块
- [✓] 获取用户列表
- [✓] 创建用户
- [✓] 更新状态
- [✓] 删除用户

### 5. 权限管理模块
- [✓] 检查权限
- [✓] 授予权限
- [✓] 撤销权限

## 发现的问题
1. 无

## 建议
1. 建议添加更多的单元测试
2. 建议增加API文档的示例

## 结论
所有核心功能测试通过，系统可以正常使用。
```

---

## 🎯 下一步

测试通过后:
1. 提交代码到GitHub
2. 创建Release版本
3. 编写用户手册
4. 部署到生产环境

---

**测试愉快！** 🎉
