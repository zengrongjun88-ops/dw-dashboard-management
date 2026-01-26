package com.dw.dashboard.controller;

import com.dw.dashboard.common.Result;
import com.dw.dashboard.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 权限控制器
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@RestController
@RequestMapping("/permission")
@Api(tags = "权限管理")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/check")
    @ApiOperation("检查用户权限")
    public Result<Boolean> checkPermission(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("报表ID") @RequestParam Long dashboardId,
            @ApiParam("权限级别") @RequestParam String permissionLevel) {
        boolean hasPermission = permissionService.hasPermission(userId, dashboardId, permissionLevel);
        return Result.success(hasPermission);
    }

    @PostMapping("/user")
    @ApiOperation("授予用户权限")
    public Result<Void> grantUserPermission(
            @ApiParam("用户ID") @RequestParam Long userId,
            @ApiParam("报表ID") @RequestParam Long dashboardId,
            @ApiParam("权限级别") @RequestParam String permissionLevel) {
        permissionService.grantUserPermission(userId, dashboardId, permissionLevel);
        return Result.success();
    }

    @PostMapping("/role")
    @ApiOperation("授予角色权限")
    public Result<Void> grantRolePermission(
            @ApiParam("角色ID") @RequestParam Long roleId,
            @ApiParam("报表ID") @RequestParam Long dashboardId,
            @ApiParam("权限级别") @RequestParam String permissionLevel) {
        permissionService.grantRolePermission(roleId, dashboardId, permissionLevel);
        return Result.success();
    }

}
