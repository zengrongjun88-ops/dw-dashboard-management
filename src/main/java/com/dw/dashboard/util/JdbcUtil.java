package com.dw.dashboard.util;

import com.dw.dashboard.common.constants.DashboardConstants;
import com.dw.dashboard.entity.DataSource;
import com.dw.dashboard.enums.DataSourceType;
import com.dw.dashboard.exception.SqlExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * JDBC工具类 - 动态SQL执行
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Component
public class JdbcUtil {

    @Autowired
    private EncryptUtil encryptUtil;

    @Autowired
    private SqlSecurityUtil sqlSecurityUtil;

    /**
     * 执行查询SQL
     *
     * @param dataSource 数据源
     * @param sql SQL语句
     * @param params 参数
     * @return 查询结果
     */
    public List<Map<String, Object>> executeQuery(DataSource dataSource, String sql, Map<String, Object> params) {
        // 1. SQL安全验证
        sqlSecurityUtil.validateSql(sql);

        // 2. 清理SQL
        String cleanSql = sqlSecurityUtil.cleanSql(sql);

        // 3. 替换参数
        String finalSql = replaceParams(cleanSql, params);

        log.info("执行SQL: {}", finalSql);

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 4. 获取连接
            conn = getConnection(dataSource);

            // 5. 设置超时
            stmt = conn.createStatement();
            stmt.setQueryTimeout(DashboardConstants.SQL_TIMEOUT);
            stmt.setMaxRows(DashboardConstants.MAX_QUERY_ROWS);

            // 6. 执行查询
            long startTime = System.currentTimeMillis();
            rs = stmt.executeQuery(finalSql);
            long endTime = System.currentTimeMillis();

            log.info("SQL执行耗时: {}ms", endTime - startTime);

            // 7. 处理结果集
            return convertResultSet(rs);

        } catch (SQLException e) {
            log.error("SQL执行失败: {}", e.getMessage(), e);
            throw new SqlExecutionException("SQL执行失败: " + e.getMessage(), e);
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    /**
     * 测试数据源连接
     *
     * @param dataSource 数据源
     * @return true-连接成功，false-连接失败
     */
    public boolean testConnection(DataSource dataSource) {
        Connection conn = null;
        try {
            conn = getConnection(dataSource);
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            log.error("数据源连接测试失败: {}", e.getMessage());
            return false;
        } finally {
            closeResources(null, null, conn);
        }
    }

    /**
     * 获取数据库连接
     */
    private Connection getConnection(DataSource dataSource) throws SQLException {
        try {
            // 获取数据源类型
            DataSourceType type = DataSourceType.getByCode(dataSource.getSourceType());
            if (type == null) {
                throw new SqlExecutionException("不支持的数据源类型: " + dataSource.getSourceType());
            }

            // 加载驱动
            Class.forName(type.getDriverClass());

            // 构建URL
            String url = type.buildUrl(dataSource.getHost(), dataSource.getPort(), dataSource.getDatabaseName());

            // 添加连接参数
            if (dataSource.getConnectionParams() != null && !dataSource.getConnectionParams().isEmpty()) {
                url += "?" + dataSource.getConnectionParams();
            }

            // 解密密码
            String password = encryptUtil.decrypt(dataSource.getPassword());

            // 获取连接
            return DriverManager.getConnection(url, dataSource.getUsername(), password);

        } catch (ClassNotFoundException e) {
            throw new SqlExecutionException("数据库驱动加载失败", e);
        }
    }

    /**
     * 替换SQL参数
     */
    private String replaceParams(String sql, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return sql;
        }

        String result = sql;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            String value = String.valueOf(entry.getValue());
            result = result.replace(placeholder, value);
        }

        return result;
    }

    /**
     * 转换结果集为List<Map>
     */
    private List<Map<String, Object>> convertResultSet(ResultSet rs) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                Object value = rs.getObject(i);
                row.put(columnName, value);
            }
            result.add(row);
        }

        return result;
    }

    /**
     * 关闭资源
     */
    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            log.error("关闭数据库资源失败", e);
        }
    }

}
