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
 * 报表定义实体
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_dashboard")
@ApiModel(description = "报表定义实体")
public class Dashboard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 报表ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "报表ID")
    private Long id;

    /**
     * 报表名称
     */
    @ApiModelProperty(value = "报表名称")
    private String dashboardName;

    /**
     * 报表编码
     */
    @ApiModelProperty(value = "报表编码")
    private String dashboardCode;

    /**
     * 报表分类
     */
    @ApiModelProperty(value = "报表分类")
    private String category;

    /**
     * 报表描述
     */
    @ApiModelProperty(value = "报表描述")
    private String description;

    /**
     * 状态：0-草稿，1-已发布，2-已下线
     */
    @ApiModelProperty(value = "状态：0-草稿，1-已发布，2-已下线")
    private Integer status;

}
