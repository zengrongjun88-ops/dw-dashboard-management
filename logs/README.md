# 日志目录说明

此目录用于存储应用运行时产生的日志文件。

## 📁 日志文件说明

### 1. 信息日志
- **文件名**: `dw-dashboard-info.log`
- **内容**: 应用的INFO级别日志
- **滚动策略**: 按日期和大小滚动
- **文件格式**: `dw-dashboard-info.YYYY-MM-DD.i.log`
- **保留期限**: 60天
- **单文件大小**: 最大100MB
- **总大小上限**: 10GB

### 2. 错误日志
- **文件名**: `dw-dashboard-error.log`
- **内容**: 应用的ERROR级别日志
- **滚动策略**: 按日期和大小滚动
- **文件格式**: `dw-dashboard-error.YYYY-MM-DD.i.log`
- **保留期限**: 60天
- **单文件大小**: 最大100MB
- **总大小上限**: 10GB

### 3. SQL执行日志
- **文件名**: `dw-dashboard-sql.log`
- **内容**: MyBatis SQL执行日志（DEBUG级别）
- **滚动策略**: 按日期和大小滚动
- **文件格式**: `dw-dashboard-sql.YYYY-MM-DD.i.log`
- **保留期限**: 30天
- **单文件大小**: 最大100MB
- **总大小上限**: 5GB

## 🔧 日志配置

日志配置文件位于：`src/main/resources/logback-spring.xml`

### 日志格式
```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
```

示例：
```
2026-01-30 10:24:35.123 [main] INFO  com.dw.dashboard.Application - Application started
```

### 日志级别
- **root**: INFO
- **com.dw.dashboard**: DEBUG
- **com.dw.dashboard.mapper**: DEBUG (SQL日志)
- **org.springframework**: WARN

## 📊 日志管理特性

### 自动滚动
- **时间滚动**: 每天自动创建新的日志文件
- **大小滚动**: 单个文件超过100MB时自动创建新文件
- **索引编号**: 同一天的多个文件使用 `.i` 索引（如 `.0.log`, `.1.log`）

### 自动清理
- 系统会自动删除超过保留期限的历史日志
- INFO/ERROR日志保留60天
- SQL日志保留30天

### 磁盘空间保护
- 设置了总大小上限，防止日志占用过多磁盘空间
- INFO/ERROR日志总大小不超过10GB
- SQL日志总大小不超过5GB

## 🚀 使用建议

### 查看实时日志
```bash
# 查看INFO日志
tail -f logs/dw-dashboard-info.log

# 查看ERROR日志
tail -f logs/dw-dashboard-error.log

# 查看SQL日志
tail -f logs/dw-dashboard-sql.log
```

### 搜索日志
```bash
# 搜索错误信息
grep "ERROR" logs/dw-dashboard-error.log

# 搜索特定SQL
grep "SELECT" logs/dw-dashboard-sql.log

# 搜索特定时间段
grep "2026-01-30 10:" logs/dw-dashboard-info.log
```

### 日志分析
```bash
# 统计错误数量
grep -c "ERROR" logs/dw-dashboard-error.log

# 查看最近的错误
tail -100 logs/dw-dashboard-error.log

# 查看慢SQL（假设耗时超过1000ms）
grep "cost.*[1-9][0-9][0-9][0-9]ms" logs/dw-dashboard-sql.log
```

## ⚠️ 注意事项

1. **权限要求**: 应用需要对此目录有写权限
2. **磁盘空间**: 确保有足够的磁盘空间存储日志
3. **性能影响**: DEBUG级别日志会影响性能，生产环境建议调整为INFO
4. **敏感信息**: 注意日志中不要记录密码、密钥等敏感信息
5. **日志轮转**: 系统会自动管理日志文件，无需手动清理

## 🔒 安全建议

- 定期检查日志文件权限（建议 644 或 640）
- 不要将日志文件提交到版本控制系统（已在 .gitignore 中配置）
- 生产环境建议将日志发送到集中式日志系统（如ELK、Splunk等）
- 定期审计日志内容，及时发现异常行为

## 📝 版本历史

- **2026-01-30**: 优化日志配置，添加大小滚动和总大小限制
- **2026-01-27**: 初始日志配置
