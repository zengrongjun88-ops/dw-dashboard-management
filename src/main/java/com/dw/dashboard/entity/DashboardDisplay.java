package com.dw.dashboard.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dw.dashboard.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 报表展示配置实体
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_dashboard_display")
@ApiModel(description = "报表展示配置实体")
public class DashboardDisplay extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 展示配置ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "展示配置ID")
    private Long id;

    /**
     * 报表ID
     */
    @ApiModelProperty(value = "报表ID")
    private Long dashboardId;

    /**
     * 查询配置ID
     */
    @ApiModelProperty(value = "查询配置ID")
    private Long queryId;

    /**
     * 图表类型：TABLE, LINE, BAR, PIE, SCATTER, AREA
     */
    @ApiModelProperty(value = "图表类型")
    private String chartType;

    /**
     * 图表配置（JSON格式）
     */
    @ApiModelProperty(value = "图表配置")
    private String chartConfig;

    /**
     * X坐标
     */
    @ApiModelProperty(value = "X坐标")
    private Integer positionX;

    /**
     * Y坐标
     */
    @ApiModelProperty(value = "Y坐标")
    private Integer positionY;

    /**
     * 宽度（栅格）
     */
    @ApiModelProperty(value = "宽度")
    private Integer width;

    /**
     * 高度（栅格）
     */
    @ApiModelProperty(value = "高度")
    private Integer height;

    /**
     * 排序顺序
     */
    @ApiModelProperty(value = "排序顺序")
    private Integer sortOrder;

}
