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
 * 查询执行日志实体
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_query_execution_log")
@ApiModel(description = "查询执行日志实体")
public class QueryExecutionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "日志ID")
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
     * 数据源ID
     */
    @ApiModelProperty(value = "数据源ID")
    private Long dataSourceId;

    /**
     * 执行的SQL
     */
    @ApiModelProperty(value = "执行的SQL")
    private String executeSql;

    /**
     * 执行参数（JSON格式）
     */
    @ApiModelProperty(value = "执行参数")
    private String executeParams;

    /**
     * 执行耗时（毫秒）
     */
    @ApiModelProperty(value = "执行耗时")
    private Integer executeTime;

    /**
     * 结果行数
     */
    @ApiModelProperty(value = "结果行数")
    private Integer resultRows;

    /**
     * 执行状态：0-失败，1-成功
     */
    @ApiModelProperty(value = "执行状态")
    private Integer status;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String errorMessage;

    /**
     * 执行用户
     */
    @ApiModelProperty(value = "执行用户")
    private String executeUser;

    /**
     * 执行IP
     */
    @ApiModelProperty(value = "执行IP")
    private String executeIp;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
