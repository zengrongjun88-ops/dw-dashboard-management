package com.dw.dashboard.dto.response;

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
 * 数据源响应
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "数据源响应")
public class DataSourceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据源ID")
    private Long id;

    @ApiModelProperty(value = "数据源名称")
    private String sourceName;

    @ApiModelProperty(value = "数据源类型")
    private String sourceType;

    @ApiModelProperty(value = "主机地址")
    private String host;

    @ApiModelProperty(value = "端口")
    private Integer port;

    @ApiModelProperty(value = "数据库名")
    private String databaseName;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "连接参数")
    private String connectionParams;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态：0-禁用，1-启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
