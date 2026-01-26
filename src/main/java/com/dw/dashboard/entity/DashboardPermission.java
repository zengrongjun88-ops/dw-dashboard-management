package com.dw.dashboard.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报表权限实体
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_dashboard_permission")
@ApiModel(description = "报表权限实体")
public class DashboardPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "权限ID")
    private Long id;

    /**
     * 报表ID
     */
    @ApiModelProperty(value = "报表ID")
    private Long dashboardId;

    /**
     * 权限类型：USER, ROLE
     */
    @ApiModelProperty(value = "权限类型")
    private String permissionType;

    /**
     * 权限对象ID（用户ID或角色ID）
     */
    @ApiModelProperty(value = "权限对象ID")
    private Long permissionId;

    /**
     * 权限级别：VIEW, EDIT, ADMIN
     */
    @ApiModelProperty(value = "权限级别")
    private String permissionLevel;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createBy;

}
