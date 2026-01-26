package com.dw.dashboard.common.constants;

/**
 * 缓存常量
 *
 * @author DW Team
 * @since 2026-01-26
 */
public class CacheConstants {

    /**
     * 缓存前缀
     */
    public static final String CACHE_PREFIX = "dw:dashboard:";

    /**
     * 数据源缓存前缀
     */
    public static final String DATASOURCE_PREFIX = CACHE_PREFIX + "datasource:";

    /**
     * 报表缓存前缀
     */
    public static final String DASHBOARD_PREFIX = CACHE_PREFIX + "dashboard:";

    /**
     * 查询结果缓存前缀
     */
    public static final String QUERY_RESULT_PREFIX = CACHE_PREFIX + "query:result:";

    /**
     * 用户缓存前缀
     */
    public static final String USER_PREFIX = CACHE_PREFIX + "user:";

    /**
     * 角色缓存前缀
     */
    public static final String ROLE_PREFIX = CACHE_PREFIX + "role:";

    /**
     * 权限缓存前缀
     */
    public static final String PERMISSION_PREFIX = CACHE_PREFIX + "permission:";

    /**
     * 默认缓存过期时间（秒）
     */
    public static final long DEFAULT_EXPIRE = 3600L;

    /**
     * 查询结果缓存过期时间（秒）
     */
    public static final long QUERY_RESULT_EXPIRE = 1800L;

    /**
     * 数据源缓存过期时间（秒）
     */
    public static final long DATASOURCE_EXPIRE = 7200L;

}
