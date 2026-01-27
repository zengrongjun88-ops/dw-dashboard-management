package com.dw.dashboard.security.auth;

import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.exception.BusinessException;
import com.dw.dashboard.security.dto.LoginRequest;
import com.dw.dashboard.security.dto.LoginResponse;
import com.dw.dashboard.security.jwt.JwtProperties;
import com.dw.dashboard.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * 本地认证服务实现
 *
 * @author DW Team
 * @since 2026-01-27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocalAuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            // 1. 认证用户
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // 2. 生成Token
            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(request.getUsername());

            log.info("用户登录成功: {}", request.getUsername());

            // 3. 返回响应
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtProperties.getExpiration() / 1000)
                    .build();

        } catch (AuthenticationException e) {
            log.error("用户登录失败: {}", e.getMessage());
            throw new BusinessException(ResultCode.UNAUTHORIZED, "用户名或密码错误");
        }
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        try {
            // 1. 验证刷新Token
            if (!jwtTokenProvider.validateToken(refreshToken)) {
                throw new BusinessException(ResultCode.UNAUTHORIZED, "刷新Token无效");
            }

            // 2. 获取用户名
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

            // 3. 生成新的Token
            String newAccessToken = jwtTokenProvider.generateAccessToken(username);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);

            log.info("刷新Token成功: {}", username);

            // 4. 返回响应
            return LoginResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtProperties.getExpiration() / 1000)
                    .build();

        } catch (Exception e) {
            log.error("刷新Token失败: {}", e.getMessage());
            throw new BusinessException(ResultCode.UNAUTHORIZED, "刷新Token失败");
        }
    }

    @Override
    public void logout(String username) {
        // TODO: 可以在这里实现Token黑名单机制
        log.info("用户登出: {}", username);
    }

}
