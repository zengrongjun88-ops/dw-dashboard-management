package com.dw.dashboard.service;

/**
 * 权限服务接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
public interface IPermissionService {

    /**
     * 检查用户是否有报表权限
     *
     * @param userId 用户ID
     * @param dashboardId 报表ID
     * @param permissionLevel 权限级别
     * @return true-有权限，false-无权限
     */
    boolean hasPermission(Long userId, Long dashboardId, String permissionLevel);

    /**
     * 授予用户报表权限
     *
     * @param userId 用户ID
     * @param dashboardId 报表ID
     * @param permissionLevel 权限级别
     */
    void grantUserPermission(Long userId, Long dashboardId, String permissionLevel);

    /**
     * 授予角色报表权限
     *
     * @param roleId 角色ID
     * @param dashboardId 报表ID
     * @param permissionLevel 权限级别
     */
    void grantRolePermission(Long roleId, Long dashboardId, String permissionLevel);

}
