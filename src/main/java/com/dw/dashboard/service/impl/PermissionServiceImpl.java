package com.dw.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dw.dashboard.entity.DashboardPermission;
import com.dw.dashboard.entity.UserRole;
import com.dw.dashboard.enums.PermissionType;
import com.dw.dashboard.mapper.DashboardPermissionMapper;
import com.dw.dashboard.mapper.UserRoleMapper;
import com.dw.dashboard.service.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限服务实现
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private DashboardPermissionMapper dashboardPermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean hasPermission(Long userId, Long dashboardId, String permissionLevel) {
        // 1. 检查用户直接权限
        LambdaQueryWrapper<DashboardPermission> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(DashboardPermission::getDashboardId, dashboardId)
                .eq(DashboardPermission::getPermissionType, PermissionType.USER.getCode())
                .eq(DashboardPermission::getPermissionId, userId)
                .eq(DashboardPermission::getPermissionLevel, permissionLevel);

        if (dashboardPermissionMapper.selectCount(userWrapper) > 0) {
            return true;
        }

        // 2. 检查角色权限
        LambdaQueryWrapper<UserRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.eq(UserRole::getUserId, userId);
        List<Long> roleIds = userRoleMapper.selectList(roleWrapper).stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        if (!roleIds.isEmpty()) {
            LambdaQueryWrapper<DashboardPermission> rolePermWrapper = new LambdaQueryWrapper<>();
            rolePermWrapper.eq(DashboardPermission::getDashboardId, dashboardId)
                    .eq(DashboardPermission::getPermissionType, PermissionType.ROLE.getCode())
                    .in(DashboardPermission::getPermissionId, roleIds)
                    .eq(DashboardPermission::getPermissionLevel, permissionLevel);

            return dashboardPermissionMapper.selectCount(rolePermWrapper) > 0;
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantUserPermission(Long userId, Long dashboardId, String permissionLevel) {
        DashboardPermission permission = DashboardPermission.builder()
                .dashboardId(dashboardId)
                .permissionType(PermissionType.USER.getCode())
                .permissionId(userId)
                .permissionLevel(permissionLevel)
                .build();

        dashboardPermissionMapper.insert(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRolePermission(Long roleId, Long dashboardId, String permissionLevel) {
        DashboardPermission permission = DashboardPermission.builder()
                .dashboardId(dashboardId)
                .permissionType(PermissionType.ROLE.getCode())
                .permissionId(roleId)
                .permissionLevel(permissionLevel)
                .build();

        dashboardPermissionMapper.insert(permission);
    }

}
