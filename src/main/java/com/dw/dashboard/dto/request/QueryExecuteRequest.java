package com.dw.dashboard.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 查询执行请求
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@ApiModel(description = "查询执行请求")
public class QueryExecuteRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询配置ID", required = true)
    @NotNull(message = "查询配置ID不能为空")
    private Long queryId;

    @ApiModelProperty(value = "查询参数")
    private Map<String, Object> params;

    @ApiModelProperty(value = "是否使用缓存", example = "true")
    private Boolean useCache = true;

}
