package com.dw.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.dto.request.UserCreateRequest;
import com.dw.dashboard.dto.response.UserResponse;
import com.dw.dashboard.entity.User;
import com.dw.dashboard.exception.BusinessException;
import com.dw.dashboard.mapper.UserMapper;
import com.dw.dashboard.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse createUser(UserCreateRequest request) {
        // 1. 检查用户名是否已存在
        User existUser = getByUsername(request.getUsername());
        if (existUser != null) {
            throw new BusinessException(ResultCode.DATA_EXISTS, "用户名已存在");
        }

        // 2. 创建用户
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword()) // TODO: 密码加密
                .realName(request.getRealName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .status(1)
                .build();

        save(user);

        // 3. 返回响应
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, Integer status) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXISTS, "用户不存在");
        }
        user.setStatus(status);
        updateById(user);
    }

}
