package com.dw.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dw.dashboard.dto.request.UserCreateRequest;
import com.dw.dashboard.dto.response.UserResponse;
import com.dw.dashboard.entity.User;

/**
 * 用户服务接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
public interface IUserService extends IService<User> {

    /**
     * 创建用户
     *
     * @param request 创建请求
     * @return 用户响应
     */
    UserResponse createUser(UserCreateRequest request);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    User getByUsername(String username);

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    void updateStatus(Long userId, Integer status);

}
