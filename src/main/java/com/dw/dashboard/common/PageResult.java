package com.dw.dashboard.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private List<T> records;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private Long total;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private Long current;

    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小")
    private Long size;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Long pages;

    public PageResult(List<T> records, Long total, Long current, Long size) {
        this.records = records;
        this.total = total;
        this.current = current;
        this.size = size;
        this.pages = (total + size - 1) / size;
    }

}
