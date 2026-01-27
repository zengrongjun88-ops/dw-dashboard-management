package com.dw.dashboard.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 刷新Token请求DTO
 *
 * @author DW Team
 * @since 2026-01-27
 */
@Data
@Schema(description = "刷新Token请求")
public class RefreshTokenRequest {

    /**
     * 刷新Token
     */
    @NotBlank(message = "刷新Token不能为空")
    @Schema(description = "刷新Token")
    private String refreshToken;

}
