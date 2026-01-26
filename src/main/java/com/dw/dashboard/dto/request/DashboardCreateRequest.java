package com.dw.dashboard.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 报表创建请求
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@ApiModel(description = "报表创建请求")
public class DashboardCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报表名称", required = true)
    @NotBlank(message = "报表名称不能为空")
    private String dashboardName;

    @ApiModelProperty(value = "报表编码", required = true)
    @NotBlank(message = "报表编码不能为空")
    private String dashboardCode;

    @ApiModelProperty(value = "报表分类")
    private String category;

    @ApiModelProperty(value = "报表描述")
    private String description;

}
