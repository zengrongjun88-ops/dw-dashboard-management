package com.dw.dashboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 图表类型枚举
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Getter
@AllArgsConstructor
public enum ChartType {

    /**
     * 表格
     */
    TABLE("TABLE", "表格"),

    /**
     * 折线图
     */
    LINE("LINE", "折线图"),

    /**
     * 柱状图
     */
    BAR("BAR", "柱状图"),

    /**
     * 饼图
     */
    PIE("PIE", "饼图"),

    /**
     * 散点图
     */
    SCATTER("SCATTER", "散点图"),

    /**
     * 面积图
     */
    AREA("AREA", "面积图"),

    /**
     * 雷达图
     */
    RADAR("RADAR", "雷达图"),

    /**
     * 漏斗图
     */
    FUNNEL("FUNNEL", "漏斗图");

    private final String code;
    private final String desc;

    public static ChartType getByCode(String code) {
        for (ChartType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
