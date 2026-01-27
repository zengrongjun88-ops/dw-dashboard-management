package com.dw.dashboard.security.auth;

import com.dw.dashboard.security.dto.LoginRequest;
import com.dw.dashboard.security.dto.LoginResponse;

/**
 * 认证服务接口
 *
 * @author DW Team
 * @since 2026-01-27
 */
public interface IAuthService {

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应（包含Token）
     */
    LoginResponse login(LoginRequest request);

    /**
     * 刷新Token
     *
     * @param refreshToken 刷新Token
     * @return 新的登录响应
     */
    LoginResponse refreshToken(String refreshToken);

    /**
     * 用户登出
     *
     * @param username 用户名
     */
    void logout(String username);

}
