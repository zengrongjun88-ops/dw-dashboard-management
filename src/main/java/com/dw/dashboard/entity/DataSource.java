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
 * 数据源实体
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("tb_data_source")
@ApiModel(description = "数据源实体")
public class DataSource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 数据源ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "数据源ID")
    private Long id;

    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "数据源名称")
    private String sourceName;

    /**
     * 数据源类型：MYSQL, POSTGRESQL, ORACLE, SQLSERVER, CLICKHOUSE
     */
    @ApiModelProperty(value = "数据源类型")
    private String sourceType;

    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址")
    private String host;

    /**
     * 端口
     */
    @ApiModelProperty(value = "端口")
    private Integer port;

    /**
     * 数据库名
     */
    @ApiModelProperty(value = "数据库名")
    private String databaseName;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码（AES加密）
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 连接参数（JSON格式）
     */
    @ApiModelProperty(value = "连接参数")
    private String connectionParams;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 状态：0-禁用，1-启用
     */
    @ApiModelProperty(value = "状态：0-禁用，1-启用")
    private Integer status;

}
