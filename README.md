# DW-Dashboard-Management 自助报表管理系统

## 项目简介

DW-Dashboard-Management 是一个基于 Spring Boot 的自助报表管理系统后端服务，支持多数据源管理、动态SQL执行、报表配置和权限控制。

## 技术栈

- **框架**: Spring Boot 3.2.2
- **构建工具**: Maven
- **数据库**: MySQL 8.0+
- **ORM框架**: MyBatis-Plus 3.5.5
- **缓存**: Redis
- **API文档**: Knife4j 4.3.0
- **工具库**: Hutool 5.8.25, Fastjson2 2.0.45
- **连接池**: Druid 1.2.21

## 核心功能

### 1. 用户与权限管理
- 用户管理（创建、查询、更新、删除）
- 角色管理（创建、查询、更新、删除）
- 用户角色关联
- 基于用户和角色的报表权限控制

### 2. 数据源管理
- 支持多种数据源类型（MySQL、PostgreSQL、Oracle、SQL Server、ClickHouse）
- 数据源连接测试
- 数据源密码AES加密存储
- 数据源状态管理

### 3. 报表管理
- 报表定义（创建、查询、更新、删除）
- 报表查询配置（SQL、参数、缓存、分页）
- 报表展示配置（图表类型、列配置、筛选器）
- 报表状态管理（草稿、已发布、已下线）
- 报表分类和标签

### 4. 报表执行
- 动态SQL执行
- SQL安全验证（防注入、危险关键字检测）
- 查询结果缓存（Redis）
- 查询超时控制
- 执行日志记录

### 5. 权限控制
- 报表权限分配（用户级、角色级）
- 权限级别控制（查看、编辑、管理）
- 公开报表访问控制

## 项目结构

```
dw-dashboard-management/
├── src/
│   ├── main/
│   │   ├── java/com/dw/dashboard/
│   │   │   ├── common/              # 公共类
│   │   │   │   ├── BaseEntity.java
│   │   │   │   ├── Result.java
│   │   │   │   ├── ResultCode.java
│   │   │   │   ├── PageResult.java
│   │   │   │   └── constants/       # 常量类
│   │   │   ├── config/              # 配置类
│   │   │   ├── controller/          # 控制器
│   │   │   ├── entity/              # 实体类
│   │   │   ├── mapper/              # Mapper接口
│   │   │   ├── service/             # 服务层
│   │   │   │   └── impl/            # 服务实现
│   │   │   ├── dto/                 # 数据传输对象
│   │   │   │   ├── request/         # 请求DTO
│   │   │   │   └── response/        # 响应DTO
│   │   │   ├── enums/               # 枚举类
│   │   │   ├── exception/           # 异常处理
│   │   │   ├── util/                # 工具类
│   │   │   └── DashboardApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── logback-spring.xml
│   │       ├── mapper/              # MyBatis XML
│   │       └── db/                  # 数据库脚本
│   │           ├── schema.sql
│   │           └── data.sql
│   └── test/                        # 测试代码
└── pom.xml
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+

### 2. 数据库初始化

```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE dw_dashboard DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 执行表结构脚本
mysql -u root -p dw_dashboard < src/main/resources/db/schema.sql

# 执行初始数据脚本
mysql -u root -p dw_dashboard < src/main/resources/db/data.sql
```

### 3. 配置修改

修改 `src/main/resources/application-dev.yml` 文件：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dw_dashboard?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password

  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password  # 如果没有密码则留空
```

### 4. 启动应用

```bash
# 使用Maven启动
mvn spring-boot:run

# 或者打包后启动
mvn clean package
java -jar target/dw-dashboard-management-1.0.0.jar
```

### 5. 访问API文档

启动成功后，访问 Knife4j API 文档：

```
http://localhost:8080/api/doc.html
```

## 默认账号

系统初始化后会创建以下默认账号：

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | 123456 | 管理员 | 拥有所有权限 |
| analyst | 123456 | 分析师 | 可创建和管理报表 |
| viewer | 123456 | 普通用户 | 只能查看报表 |

**注意**: 生产环境请务必修改默认密码！

## API接口

### 用户管理
- `POST /api/user/create` - 创建用户
- `GET /api/user/list` - 获取用户列表
- `GET /api/user/detail/{id}` - 获取用户详情
- `PUT /api/user/status/{id}` - 修改用户状态
- `DELETE /api/user/delete/{id}` - 删除用户

### 角色管理
- `GET /api/role/list` - 获取角色列表
- `GET /api/role/all` - 获取所有角色

### 数据源管理
- `POST /api/datasource/create` - 创建数据源
- `GET /api/datasource/list` - 获取数据源列表
- `GET /api/datasource/detail/{id}` - 获取数据源详情
- `POST /api/datasource/test` - 测试数据源连接
- `PUT /api/datasource/status/{id}` - 修改数据源状态
- `DELETE /api/datasource/delete/{id}` - 删除数据源

### 报表管理
- `POST /api/dashboard/create` - 创建报表
- `GET /api/dashboard/list` - 获取报表列表
- `GET /api/dashboard/detail/{id}` - 获取报表详情
- `PUT /api/dashboard/publish/{id}` - 发布报表
- `PUT /api/dashboard/offline/{id}` - 下线报表
- `DELETE /api/dashboard/delete/{id}` - 删除报表

### 报表执行
- `POST /api/dashboard/execute` - 执行报表查询
- `DELETE /api/dashboard/cache/{dashboardId}` - 清除报表缓存

### 权限管理
- `GET /api/permission/check` - 检查用户权限
- `POST /api/permission/grant/user` - 授予用户权限
- `POST /api/permission/grant/role` - 授予角色权限

## 核心特性

### 1. SQL安全防护

系统实现了多层SQL安全防护机制：

- **SQL类型检测**: 只允许执行SELECT语句
- **危险关键字检测**: 拦截DROP、DELETE、UPDATE、TRUNCATE、ALTER等危险操作
- **注释过滤**: 禁止SQL注释（--、/* */）
- **多语句检测**: 禁止执行多条SQL语句
- **参数化查询**: 使用PreparedStatement防止SQL注入

### 2. 数据源密码加密

数据源密码使用AES加密算法存储，确保数据安全：

```java
// 加密
String encryptedPassword = EncryptUtil.encrypt(plainPassword);

// 解密
String plainPassword = EncryptUtil.decrypt(encryptedPassword);
```

### 3. 查询结果缓存

支持Redis缓存查询结果，提升性能：

- 可配置缓存开关
- 可配置缓存过期时间
- 支持手动清除缓存

### 4. 执行日志记录

记录所有SQL执行历史，包括：

- 执行SQL
- 执行参数
- 执行用户
- 执行状态
- 执行时长
- 结果行数
- 错误信息

## 数据库表结构

系统包含9个核心表：

1. **sys_user** - 用户表
2. **sys_role** - 角色表
3. **sys_user_role** - 用户角色关联表
4. **dashboard_datasource** - 数据源配置表
5. **dashboard_definition** - 报表定义表
6. **dashboard_query_config** - 报表查询配置表
7. **dashboard_display_config** - 报表展示配置表
8. **dashboard_permission** - 报表权限表
9. **query_execution_log** - 查询执行日志表

详细表结构请查看 `src/main/resources/db/schema.sql`

## 配置说明

### 应用配置

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  profiles:
    active: dev  # 环境：dev, prod
```

### 数据库配置

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/dw_dashboard
    username: root
    password: your_password
```

### Redis配置

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:
      database: 0
      timeout: 3000ms
```

### MyBatis-Plus配置

```yaml
mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.dw.dashboard.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

## 开发指南

### 添加新的数据源类型

1. 在 `DataSourceType` 枚举中添加新类型
2. 在 `JdbcUtil` 中添加对应的驱动类名
3. 测试连接功能

### 添加新的图表类型

1. 在 `ChartType` 枚举中添加新类型
2. 前端实现对应的图表渲染逻辑

### 自定义SQL安全规则

修改 `SqlSecurityUtil` 类中的 `DANGEROUS_KEYWORDS` 常量。

## 常见问题

### 1. 启动时报数据库连接错误

检查 `application-dev.yml` 中的数据库配置是否正确，确保MySQL服务已启动。

### 2. Redis连接失败

检查Redis服务是否启动，配置的host和port是否正确。

### 3. API文档无法访问

确认应用已成功启动，访问地址为 `http://localhost:8080/api/doc.html`

### 4. SQL执行被拦截

检查SQL语句是否包含危险关键字，系统只允许执行SELECT语句。

## 安全建议

1. **修改默认密码**: 生产环境务必修改所有默认账号密码
2. **配置HTTPS**: 生产环境建议使用HTTPS协议
3. **限制IP访问**: 配置防火墙规则，限制数据库和Redis的访问IP
4. **定期备份**: 定期备份数据库数据
5. **日志审计**: 定期检查执行日志，发现异常行为
6. **密钥管理**: 妥善保管AES加密密钥

## 许可证

本项目采用 MIT 许可证。

## 联系方式

如有问题或建议，请提交 Issue 或 Pull Request。

---

**注意**: 本系统仅供学习和研究使用，生产环境使用前请进行充分的安全评估和测试。
