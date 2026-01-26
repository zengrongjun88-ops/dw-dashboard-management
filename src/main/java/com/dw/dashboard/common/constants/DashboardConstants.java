package com.dw.dashboard.common.constants;

/**
 * 报表常量
 *
 * @author DW Team
 * @since 2026-01-26
 */
public class DashboardConstants {

    /**
     * SQL执行超时时间（秒）
     */
    public static final int SQL_TIMEOUT = 30;

    /**
     * 查询结果最大行数
     */
    public static final int MAX_QUERY_ROWS = 10000;

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大每页大小
     */
    public static final int MAX_PAGE_SIZE = 1000;

    /**
     * 报表状态：草稿
     */
    public static final int DASHBOARD_STATUS_DRAFT = 0;

    /**
     * 报表状态：已发布
     */
    public static final int DASHBOARD_STATUS_PUBLISHED = 1;

    /**
     * 报表状态：已下线
     */
    public static final int DASHBOARD_STATUS_OFFLINE = 2;

    /**
     * 用户状态：禁用
     */
    public static final int USER_STATUS_DISABLED = 0;

    /**
     * 用户状态：启用
     */
    public static final int USER_STATUS_ENABLED = 1;

    /**
     * 数据源状态：禁用
     */
    public static final int DATASOURCE_STATUS_DISABLED = 0;

    /**
     * 数据源状态：启用
     */
    public static final int DATASOURCE_STATUS_ENABLED = 1;

    /**
     * 执行状态：失败
     */
    public static final int EXECUTE_STATUS_FAILED = 0;

    /**
     * 执行状态：成功
     */
    public static final int EXECUTE_STATUS_SUCCESS = 1;

}
