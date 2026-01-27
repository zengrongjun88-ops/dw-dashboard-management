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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse createUser(UserCreateRequest request) {
        // 1. 检查用户名是否已存在
        User existUser = getByUsername(request.getUsername());
        if (existUser != null) {
            throw new BusinessException(ResultCode.DATA_EXISTS, "用户名已存在");
        }

        // 2. 检查邮箱是否已存在
        if (request.getEmail() != null) {
            LambdaQueryWrapper<User> emailWrapper = new LambdaQueryWrapper<>();
            emailWrapper.eq(User::getEmail, request.getEmail());
            if (count(emailWrapper) > 0) {
                throw new BusinessException(ResultCode.DATA_EXISTS, "邮箱已被使用");
            }
        }

        // 3. 创建用户（密码加密）
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .realName(request.getRealName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .status(1)
                .build();

        save(user);
        log.info("创建用户成功: {}", user.getUsername());

        // 4. 返回响应
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
        log.info("更新用户状态成功: userId={}, status={}", userId, status);
    }

}
