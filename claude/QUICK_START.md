# 快速启动指南

## 🚀 5分钟快速启动

### 前置要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+
- Node.js 18+
- npm 或 yarn

---

## 一、后端启动

### 1. 数据库初始化

```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE dw_dashboard DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 执行表结构脚本
mysql -u root -p dw_dashboard < src/main/resources/db/schema.sql

# 执行初始数据脚本
mysql -u root -p dw_dashboard < src/main/resources/db/data.sql
```

### 2. 启动Redis

```bash
redis-server
```

### 3. 修改配置

编辑 `src/main/resources/application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dw_dashboard?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password  # 修改为你的MySQL密码

  data:
    redis:
      host: localhost
      port: 6379
      password:  # 如果Redis有密码则填写
```

### 4. 启动后端

```bash
# 方式1: 使用Maven
mvn spring-boot:run

# 方式2: 打包后启动
mvn clean package -DskipTests
java -jar target/dw-dashboard-management-1.0.0.jar
```

### 5. 验证后端

访问API文档: http://localhost:8080/api/doc.html

---

## 二、前端启动

### 1. 安装依赖

```bash
cd frontend
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

### 3. 访问前端

浏览器打开: http://localhost:3000

---

## 三、登录测试

使用以下任一账号登录:

| 用户名 | 密码 | 角色 | 权限 |
|--------|------|------|------|
| admin | admin123 | 管理员 | 所有权限 |
| analyst | admin123 | 分析师 | 创建和管理报表 |
| viewer | admin123 | 查看者 | 只能查看报表 |

---

## 四、功能验证

### 1. 登录系统
- 访问 http://localhost:3000
- 使用 admin/admin123 登录

### 2. 数据源管理
- 点击左侧菜单"数据源管理" -> "数据源列表"
- 查看已有的示例数据源
- 点击"创建数据源"测试创建功能

### 3. 报表管理
- 点击左侧菜单"报表管理" -> "报表列表"
- 查看已有的示例报表
- 点击"查看"按钮查看报表详情

### 4. 用户管理
- 点击左侧菜单"用户管理" -> "用户列表"
- 查看系统用户
- 测试创建、编辑、删除用户功能

---

## 五、常见问题

### 1. 后端启动失败

**问题**: 数据库连接失败
```
解决方案:
1. 检查MySQL是否启动
2. 检查application-dev.yml中的数据库配置
3. 确认数据库dw_dashboard已创建
```

**问题**: Redis连接失败
```
解决方案:
1. 检查Redis是否启动: redis-cli ping
2. 检查Redis配置是否正确
```

### 2. 前端启动失败

**问题**: 依赖安装失败
```
解决方案:
1. 删除node_modules和package-lock.json
2. 重新执行 npm install
3. 或使用 npm install --legacy-peer-deps
```

**问题**: 端口被占用
```
解决方案:
1. 修改vite.config.ts中的端口号
2. 或关闭占用3000端口的程序
```

### 3. 登录失败

**问题**: 401 Unauthorized
```
解决方案:
1. 检查后端是否正常启动
2. 检查数据库初始化是否成功
3. 确认用户名密码正确
```

### 4. API调用失败

**问题**: CORS跨域错误
```
解决方案:
1. 开发环境已配置Vite代理,应该不会出现
2. 如果出现,检查vite.config.ts中的proxy配置
```

---

## 六、开发模式

### 后端热重载

使用Spring Boot DevTools:

```xml
<!-- pom.xml中已包含 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

### 前端热重载

Vite默认支持热重载,修改代码后自动刷新。

---

## 七、生产部署

### 1. 后端打包

```bash
mvn clean package -DskipTests
```

生成文件: `target/dw-dashboard-management-1.0.0.jar`

### 2. 前端打包

```bash
cd frontend
npm run build
```

生成文件: `frontend/dist/`

### 3. 部署建议

**后端**:
- 使用systemd或supervisor管理Java进程
- 配置Nginx反向代理
- 使用生产环境配置文件

**前端**:
- 使用Nginx托管静态文件
- 配置gzip压缩
- 配置缓存策略

---

## 八、Docker部署(可选)

### 1. 构建镜像

```bash
# 后端
docker build -t dw-dashboard-backend .

# 前端
cd frontend
docker build -t dw-dashboard-frontend .
```

### 2. 使用Docker Compose

```bash
docker-compose up -d
```

---

## 九、监控和日志

### 查看后端日志

```bash
# 实时查看日志
tail -f logs/spring.log

# 查看错误日志
grep ERROR logs/spring.log
```

### 查看前端日志

浏览器开发者工具 -> Console

---

## 十、性能优化建议

1. **数据库优化**
   - 添加适当的索引
   - 定期清理日志表
   - 使用连接池

2. **缓存优化**
   - 合理设置缓存过期时间
   - 使用Redis集群

3. **前端优化**
   - 启用代码分割
   - 使用CDN加速
   - 图片懒加载

---

## 📞 获取帮助

- **查看文档**: claude/PROJECT_COMPLETION_SUMMARY.md
- **前端指南**: claude/FRONTEND_IMPLEMENTATION_GUIDE.md
- **API文档**: http://localhost:8080/api/doc.html
- **GitHub Issues**: https://github.com/zengrongjun88-ops/dw-dashboard-management/issues

---

**祝您使用愉快！** 🎉
