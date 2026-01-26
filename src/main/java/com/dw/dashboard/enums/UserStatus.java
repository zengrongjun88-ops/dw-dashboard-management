package com.dw.dashboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Getter
@AllArgsConstructor
public enum UserStatus {

    /**
     * 禁用
     */
    DISABLED(0, "禁用"),

    /**
     * 启用
     */
    ENABLED(1, "启用");

    private final Integer code;
    private final String desc;

    public static UserStatus getByCode(Integer code) {
        for (UserStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
