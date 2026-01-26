package com.dw.dashboard.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 查询结果响应
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "查询结果响应")
public class QueryResultResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询配置ID")
    private Long queryId;

    @ApiModelProperty(value = "查询名称")
    private String queryName;

    @ApiModelProperty(value = "执行SQL")
    private String executeSql;

    @ApiModelProperty(value = "执行耗时（毫秒）")
    private Integer executeTime;

    @ApiModelProperty(value = "结果行数")
    private Integer resultRows;

    @ApiModelProperty(value = "查询结果数据")
    private List<Map<String, Object>> data;

    @ApiModelProperty(value = "是否来自缓存")
    private Boolean fromCache;

}
