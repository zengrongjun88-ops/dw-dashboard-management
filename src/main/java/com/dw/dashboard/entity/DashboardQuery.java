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
 * 报表查询配置实体
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_dashboard_query")
@ApiModel(description = "报表查询配置实体")
public class DashboardQuery extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 查询配置ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "查询配置ID")
    private Long id;

    /**
     * 报表ID
     */
    @ApiModelProperty(value = "报表ID")
    private Long dashboardId;

    /**
     * 数据源ID
     */
    @ApiModelProperty(value = "数据源ID")
    private Long dataSourceId;

    /**
     * 查询名称
     */
    @ApiModelProperty(value = "查询名称")
    private String queryName;

    /**
     * SQL查询语句
     */
    @ApiModelProperty(value = "SQL查询语句")
    private String querySql;

    /**
     * 查询参数配置（JSON格式）
     */
    @ApiModelProperty(value = "查询参数配置")
    private String queryParams;

    /**
     * 是否启用缓存：0-否，1-是
     */
    @ApiModelProperty(value = "是否启用缓存")
    private Integer cacheEnabled;

    /**
     * 缓存过期时间（秒）
     */
    @ApiModelProperty(value = "缓存过期时间")
    private Integer cacheExpire;

    /**
     * 排序顺序
     */
    @ApiModelProperty(value = "排序顺序")
    private Integer sortOrder;

}
