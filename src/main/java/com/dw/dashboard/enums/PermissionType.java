package com.dw.dashboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限类型枚举
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Getter
@AllArgsConstructor
public enum PermissionType {

    /**
     * 用户权限
     */
    USER("USER", "用户权限"),

    /**
     * 角色权限
     */
    ROLE("ROLE", "角色权限");

    private final String code;
    private final String desc;

    public static PermissionType getByCode(String code) {
        for (PermissionType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
