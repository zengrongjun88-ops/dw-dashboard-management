-- 数据仓库报表管理系统初始数据脚本
USE dw_dashboard;

-- 插入默认用户（密码：123456，需要在应用中加密）
INSERT INTO `tb_user` (`username`, `password`, `real_name`, `email`, `phone`, `status`, `create_by`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', 'admin@dw.com', '13800138000', 1, 'system'),
('analyst', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '数据分析师', 'analyst@dw.com', '13800138001', 1, 'system'),
('viewer', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '普通用户', 'viewer@dw.com', '13800138002', 1, 'system');

-- 插入默认角色
INSERT INTO `tb_role` (`role_name`, `role_code`, `description`, `create_by`) VALUES
('系统管理员', 'ROLE_ADMIN', '拥有系统所有权限', 'system'),
('数据分析师', 'ROLE_ANALYST', '可以创建和编辑报表', 'system'),
('普通用户', 'ROLE_VIEWER', '只能查看报表', 'system');

-- 插入用户角色关联
INSERT INTO `tb_user_role` (`user_id`, `role_id`, `create_by`) VALUES
(1, 1, 'system'),
(2, 2, 'system'),
(3, 3, 'system');

-- 插入示例数据源
INSERT INTO `tb_data_source` (`source_name`, `source_type`, `host`, `port`, `database_name`, `username`, `password`, `description`, `status`, `create_by`) VALUES
('本地MySQL', 'MYSQL', 'localhost', 3306, 'dw_dashboard', 'root', 'encrypted_password', '本地MySQL数据库', 1, 'admin'),
('测试PostgreSQL', 'POSTGRESQL', 'localhost', 5432, 'test_db', 'postgres', 'encrypted_password', '测试PostgreSQL数据库', 1, 'admin');

-- 插入示例报表
INSERT INTO `tb_dashboard` (`dashboard_name`, `dashboard_code`, `category`, `description`, `status`, `create_by`) VALUES
('用户统计报表', 'USER_STATS', '用户分析', '展示用户注册和活跃情况', 1, 'admin'),
('销售数据报表', 'SALES_REPORT', '销售分析', '展示销售数据和趋势', 1, 'admin'),
('系统监控报表', 'SYSTEM_MONITOR', '系统监控', '展示系统运行状态', 1, 'admin');

-- 插入示例查询配置
INSERT INTO `tb_dashboard_query` (`dashboard_id`, `data_source_id`, `query_name`, `query_sql`, `cache_enabled`, `cache_expire`, `sort_order`, `create_by`) VALUES
(1, 1, '用户总数统计', 'SELECT COUNT(*) as total_users, COUNT(CASE WHEN status = 1 THEN 1 END) as active_users FROM tb_user WHERE deleted = 0', 1, 3600, 1, 'admin'),
(1, 1, '用户注册趋势', 'SELECT DATE(create_time) as date, COUNT(*) as count FROM tb_user WHERE deleted = 0 GROUP BY DATE(create_time) ORDER BY date DESC LIMIT 30', 1, 1800, 2, 'admin'),
(2, 1, '销售总额统计', 'SELECT SUM(amount) as total_amount, COUNT(*) as order_count FROM tb_order WHERE status = 1', 1, 600, 1, 'admin');

-- 插入示例展示配置
INSERT INTO `tb_dashboard_display` (`dashboard_id`, `query_id`, `chart_type`, `position_x`, `position_y`, `width`, `height`, `sort_order`, `create_by`) VALUES
(1, 1, 'TABLE', 0, 0, 12, 4, 1, 'admin'),
(1, 2, 'LINE', 0, 4, 12, 8, 2, 'admin'),
(2, 3, 'BAR', 0, 0, 12, 6, 1, 'admin');

-- 插入示例权限配置
INSERT INTO `tb_dashboard_permission` (`dashboard_id`, `permission_type`, `permission_id`, `permission_level`, `create_by`) VALUES
(1, 'ROLE', 1, 'ADMIN', 'admin'),
(1, 'ROLE', 2, 'EDIT', 'admin'),
(1, 'ROLE', 3, 'VIEW', 'admin'),
(2, 'ROLE', 1, 'ADMIN', 'admin'),
(2, 'ROLE', 2, 'EDIT', 'admin'),
(3, 'USER', 1, 'ADMIN', 'admin');
