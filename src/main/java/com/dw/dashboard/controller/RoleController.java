package com.dw.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dw.dashboard.common.PageResult;
import com.dw.dashboard.common.Result;
import com.dw.dashboard.entity.Role;
import com.dw.dashboard.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询角色")
    public Result<Role> getById(@ApiParam("角色ID") @PathVariable Long id) {
        Role role = roleService.getById(id);
        if (role == null) {
            return Result.error("角色不存在");
        }
        return Result.success(role);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询角色")
    public Result<PageResult<Role>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Long current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Long size,
            @ApiParam("角色名称") @RequestParam(required = false) String roleName) {

        Page<Role> page = new Page<>(current, size);
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (roleName != null && !roleName.isEmpty()) {
            wrapper.like(Role::getRoleName, roleName);
        }
        wrapper.orderByDesc(Role::getCreateTime);

        Page<Role> rolePage = roleService.page(page, wrapper);

        PageResult<Role> result = new PageResult<>(
                rolePage.getRecords(),
                rolePage.getTotal(),
                rolePage.getCurrent(),
                rolePage.getSize()
        );

        return Result.success(result);
    }

    @GetMapping("/list")
    @ApiOperation("查询所有角色")
    public Result<List<Role>> list() {
        List<Role> roles = roleService.list();
        return Result.success(roles);
    }

}
