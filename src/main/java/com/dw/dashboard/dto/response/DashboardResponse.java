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
 * 报表响应
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "报表响应")
public class DashboardResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报表ID")
    private Long id;

    @ApiModelProperty(value = "报表名称")
    private String dashboardName;

    @ApiModelProperty(value = "报表编码")
    private String dashboardCode;

    @ApiModelProperty(value = "报表分类")
    private String category;

    @ApiModelProperty(value = "报表描述")
    private String description;

    @ApiModelProperty(value = "状态：0-草稿，1-已发布，2-已下线")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

}
