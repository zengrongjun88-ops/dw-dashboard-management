package com.dw.dashboard.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据源创建请求
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@ApiModel(description = "数据源创建请求")
public class DataSourceCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据源名称", required = true)
    @NotBlank(message = "数据源名称不能为空")
    private String sourceName;

    @ApiModelProperty(value = "数据源类型", required = true)
    @NotBlank(message = "数据源类型不能为空")
    private String sourceType;

    @ApiModelProperty(value = "主机地址", required = true)
    @NotBlank(message = "主机地址不能为空")
    private String host;

    @ApiModelProperty(value = "端口", required = true)
    @NotNull(message = "端口不能为空")
    private Integer port;

    @ApiModelProperty(value = "数据库名", required = true)
    @NotBlank(message = "数据库名不能为空")
    private String databaseName;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "连接参数")
    private String connectionParams;

    @ApiModelProperty(value = "描述")
    private String description;

}
