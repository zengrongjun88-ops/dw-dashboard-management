package com.dw.dashboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据源类型枚举
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Getter
@AllArgsConstructor
public enum DataSourceType {

    /**
     * MySQL
     */
    MYSQL("MYSQL", "MySQL", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://{host}:{port}/{database}"),

    /**
     * PostgreSQL
     */
    POSTGRESQL("POSTGRESQL", "PostgreSQL", "org.postgresql.Driver", "jdbc:postgresql://{host}:{port}/{database}"),

    /**
     * Oracle
     */
    ORACLE("ORACLE", "Oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@{host}:{port}:{database}"),

    /**
     * SQL Server
     */
    SQLSERVER("SQLSERVER", "SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://{host}:{port};databaseName={database}"),

    /**
     * ClickHouse
     */
    CLICKHOUSE("CLICKHOUSE", "ClickHouse", "ru.yandex.clickhouse.ClickHouseDriver", "jdbc:clickhouse://{host}:{port}/{database}");

    private final String code;
    private final String name;
    private final String driverClass;
    private final String urlTemplate;

    public static DataSourceType getByCode(String code) {
        for (DataSourceType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 构建JDBC URL
     */
    public String buildUrl(String host, Integer port, String database) {
        return urlTemplate
                .replace("{host}", host)
                .replace("{port}", String.valueOf(port))
                .replace("{database}", database);
    }

}
