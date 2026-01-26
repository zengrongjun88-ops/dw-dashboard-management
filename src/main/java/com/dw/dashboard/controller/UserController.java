package com.dw.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dw.dashboard.common.PageResult;
import com.dw.dashboard.common.Result;
import com.dw.dashboard.dto.request.UserCreateRequest;
import com.dw.dashboard.dto.response.UserResponse;
import com.dw.dashboard.entity.User;
import com.dw.dashboard.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户控制器
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    @ApiOperation("创建用户")
    public Result<UserResponse> create(@Validated @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return Result.success(response);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询用户")
    public Result<UserResponse> getById(@ApiParam("用户ID") @PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return Result.success(response);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询用户")
    public Result<PageResult<UserResponse>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Long current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Long size,
            @ApiParam("用户名") @RequestParam(required = false) String username) {

        Page<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            wrapper.like(User::getUsername, username);
        }
        wrapper.orderByDesc(User::getCreateTime);

        Page<User> userPage = userService.page(page, wrapper);

        List<UserResponse> records = userPage.getRecords().stream().map(user -> {
            UserResponse response = new UserResponse();
            BeanUtils.copyProperties(user, response);
            return response;
        }).collect(Collectors.toList());

        PageResult<UserResponse> result = new PageResult<>(
                records,
                userPage.getTotal(),
                userPage.getCurrent(),
                userPage.getSize()
        );

        return Result.success(result);
    }

    @PutMapping("/{id}/status")
    @ApiOperation("更新用户状态")
    public Result<Void> updateStatus(
            @ApiParam("用户ID") @PathVariable Long id,
            @ApiParam("状态") @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Result<Void> delete(@ApiParam("用户ID") @PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }

}
