package com.dw.dashboard.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性
 *
 * @author DW Team
 * @since 2026-01-27
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT密钥
     */
    private String secret = "dw-dashboard-secret-key-for-jwt-token-generation-2026";

    /**
     * Token有效期（毫秒）
     */
    private Long expiration = 86400000L; // 24小时

    /**
     * Refresh Token有效期（毫秒）
     */
    private Long refreshExpiration = 604800000L; // 7天

    /**
     * Token前缀
     */
    private String tokenPrefix = "Bearer ";

    /**
     * Token请求头名称
     */
    private String header = "Authorization";

}
