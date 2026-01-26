package com.dw.dashboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 报表状态枚举
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Getter
@AllArgsConstructor
public enum DashboardStatus {

    /**
     * 草稿
     */
    DRAFT(0, "草稿"),

    /**
     * 已发布
     */
    PUBLISHED(1, "已发布"),

    /**
     * 已下线
     */
    OFFLINE(2, "已下线");

    private final Integer code;
    private final String desc;

    public static DashboardStatus getByCode(Integer code) {
        for (DashboardStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
