package com.dw.dashboard.common.constants;

/**
 * 安全常量
 *
 * @author DW Team
 * @since 2026-01-26
 */
public class SecurityConstants {

    /**
     * 用户ID请求头
     */
    public static final String USER_ID_HEADER = "X-User-Id";

    /**
     * 用户名请求头
     */
    public static final String USERNAME_HEADER = "X-Username";

    /**
     * 令牌请求头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 超级管理员角色编码
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 数据分析师角色编码
     */
    public static final String ROLE_ANALYST = "ROLE_ANALYST";

    /**
     * 普通用户角色编码
     */
    public static final String ROLE_VIEWER = "ROLE_VIEWER";

}
