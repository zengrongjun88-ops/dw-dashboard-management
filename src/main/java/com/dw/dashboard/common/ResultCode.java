package com.dw.dashboard.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    ERROR(500, "操作失败"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(600, "业务异常"),

    /**
     * 数据源异常
     */
    DATASOURCE_ERROR(601, "数据源异常"),

    /**
     * SQL执行异常
     */
    SQL_EXECUTION_ERROR(602, "SQL执行异常"),

    /**
     * 权限不足
     */
    PERMISSION_DENIED(603, "权限不足"),

    /**
     * 数据已存在
     */
    DATA_EXISTS(604, "数据已存在"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXISTS(605, "数据不存在"),

    /**
     * SQL安全检查失败
     */
    SQL_SECURITY_ERROR(606, "SQL安全检查失败"),

    /**
     * 缓存异常
     */
    CACHE_ERROR(607, "缓存异常"),

    /**
     * 加密解密异常
     */
    ENCRYPT_ERROR(608, "加密解密异常"),

    /**
     * 功能未实现
     */
    NOT_IMPLEMENTED(609, "功能未实现"),

    /**
     * 内部错误
     */
    INTERNAL_ERROR(610, "内部错误");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

}
