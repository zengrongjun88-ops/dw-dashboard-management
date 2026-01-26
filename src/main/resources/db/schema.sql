-- 数据仓库报表管理系统数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS dw_dashboard DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE dw_dashboard;

-- 1. 用户表
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码（加密）',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 角色表
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 3. 用户角色关联表
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 4. 数据源表
DROP TABLE IF EXISTS `tb_data_source`;
CREATE TABLE `tb_data_source` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '数据源ID',
  `source_name` VARCHAR(100) NOT NULL COMMENT '数据源名称',
  `source_type` VARCHAR(20) NOT NULL COMMENT '数据源类型：MYSQL, POSTGRESQL, ORACLE, SQLSERVER, CLICKHOUSE',
  `host` VARCHAR(100) NOT NULL COMMENT '主机地址',
  `port` INT NOT NULL COMMENT '端口',
  `database_name` VARCHAR(100) NOT NULL COMMENT '数据库名',
  `username` VARCHAR(100) NOT NULL COMMENT '用户名',
  `password` VARCHAR(200) NOT NULL COMMENT '密码（AES加密）',
  `connection_params` VARCHAR(500) DEFAULT NULL COMMENT '连接参数（JSON格式）',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_source_name` (`source_name`),
  KEY `idx_source_type` (`source_type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据源表';

-- 5. 报表定义表
DROP TABLE IF EXISTS `tb_dashboard`;
CREATE TABLE `tb_dashboard` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `dashboard_name` VARCHAR(100) NOT NULL COMMENT '报表名称',
  `dashboard_code` VARCHAR(100) NOT NULL COMMENT '报表编码',
  `category` VARCHAR(50) DEFAULT NULL COMMENT '报表分类',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '报表描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-草稿，1-已发布，2-已下线',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dashboard_code` (`dashboard_code`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表定义表';

-- 6. 报表查询配置表
DROP TABLE IF EXISTS `tb_dashboard_query`;
CREATE TABLE `tb_dashboard_query` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '查询配置ID',
  `dashboard_id` BIGINT NOT NULL COMMENT '报表ID',
  `data_source_id` BIGINT NOT NULL COMMENT '数据源ID',
  `query_name` VARCHAR(100) NOT NULL COMMENT '查询名称',
  `query_sql` TEXT NOT NULL COMMENT 'SQL查询语句',
  `query_params` TEXT DEFAULT NULL COMMENT '查询参数配置（JSON格式）',
  `cache_enabled` TINYINT NOT NULL DEFAULT 0 COMMENT '是否启用缓存：0-否，1-是',
  `cache_expire` INT DEFAULT 3600 COMMENT '缓存过期时间（秒）',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_dashboard_id` (`dashboard_id`),
  KEY `idx_data_source_id` (`data_source_id`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表查询配置表';

-- 7. 报表展示配置表
DROP TABLE IF EXISTS `tb_dashboard_display`;
CREATE TABLE `tb_dashboard_display` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '展示配置ID',
  `dashboard_id` BIGINT NOT NULL COMMENT '报表ID',
  `query_id` BIGINT NOT NULL COMMENT '查询配置ID',
  `chart_type` VARCHAR(20) NOT NULL COMMENT '图表类型：TABLE, LINE, BAR, PIE, SCATTER, AREA',
  `chart_config` TEXT DEFAULT NULL COMMENT '图表配置（JSON格式）',
  `position_x` INT NOT NULL DEFAULT 0 COMMENT 'X坐标',
  `position_y` INT NOT NULL DEFAULT 0 COMMENT 'Y坐标',
  `width` INT NOT NULL DEFAULT 12 COMMENT '宽度（栅格）',
  `height` INT NOT NULL DEFAULT 6 COMMENT '高度（栅格）',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序顺序',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_dashboard_id` (`dashboard_id`),
  KEY `idx_query_id` (`query_id`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表展示配置表';

-- 8. 报表权限表
DROP TABLE IF EXISTS `tb_dashboard_permission`;
CREATE TABLE `tb_dashboard_permission` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `dashboard_id` BIGINT NOT NULL COMMENT '报表ID',
  `permission_type` VARCHAR(20) NOT NULL COMMENT '权限类型：USER, ROLE',
  `permission_id` BIGINT NOT NULL COMMENT '权限对象ID（用户ID或角色ID）',
  `permission_level` VARCHAR(20) NOT NULL COMMENT '权限级别：VIEW, EDIT, ADMIN',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission` (`dashboard_id`, `permission_type`, `permission_id`),
  KEY `idx_dashboard_id` (`dashboard_id`),
  KEY `idx_permission_type` (`permission_type`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表权限表';

-- 9. 查询执行日志表
DROP TABLE IF EXISTS `tb_query_execution_log`;
CREATE TABLE `tb_query_execution_log` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `dashboard_id` BIGINT NOT NULL COMMENT '报表ID',
  `query_id` BIGINT NOT NULL COMMENT '查询配置ID',
  `data_source_id` BIGINT NOT NULL COMMENT '数据源ID',
  `execute_sql` TEXT NOT NULL COMMENT '执行的SQL',
  `execute_params` TEXT DEFAULT NULL COMMENT '执行参数（JSON格式）',
  `execute_time` INT NOT NULL COMMENT '执行耗时（毫秒）',
  `result_rows` INT NOT NULL COMMENT '结果行数',
  `status` TINYINT NOT NULL COMMENT '执行状态：0-失败，1-成功',
  `error_message` TEXT DEFAULT NULL COMMENT '错误信息',
  `execute_user` VARCHAR(50) NOT NULL COMMENT '执行用户',
  `execute_ip` VARCHAR(50) DEFAULT NULL COMMENT '执行IP',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_dashboard_id` (`dashboard_id`),
  KEY `idx_query_id` (`query_id`),
  KEY `idx_data_source_id` (`data_source_id`),
  KEY `idx_execute_user` (`execute_user`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='查询执行日志表';
