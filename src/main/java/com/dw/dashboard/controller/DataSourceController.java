package com.dw.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dw.dashboard.common.PageResult;
import com.dw.dashboard.common.Result;
import com.dw.dashboard.dto.request.DataSourceCreateRequest;
import com.dw.dashboard.dto.response.DataSourceResponse;
import com.dw.dashboard.entity.DataSource;
import com.dw.dashboard.service.IDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据源控制器
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@RestController
@RequestMapping("/datasource")
@Api(tags = "数据源管理")
public class DataSourceController {

    @Autowired
    private IDataSourceService dataSourceService;

    @PostMapping
    @ApiOperation("创建数据源")
    public Result<DataSourceResponse> create(@Validated @RequestBody DataSourceCreateRequest request) {
        DataSourceResponse response = dataSourceService.createDataSource(request);
        return Result.success(response);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询数据源")
    public Result<DataSourceResponse> getById(@ApiParam("数据源ID") @PathVariable Long id) {
        DataSource dataSource = dataSourceService.getById(id);
        if (dataSource == null) {
            return Result.error("数据源不存在");
        }
        DataSourceResponse response = new DataSourceResponse();
        BeanUtils.copyProperties(dataSource, response);
        response.setPassword(null); // 不返回密码
        return Result.success(response);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询数据源")
    public Result<PageResult<DataSourceResponse>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Long current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Long size,
            @ApiParam("数据源名称") @RequestParam(required = false) String sourceName,
            @ApiParam("数据源类型") @RequestParam(required = false) String sourceType) {

        Page<DataSource> page = new Page<>(current, size);
        LambdaQueryWrapper<DataSource> wrapper = new LambdaQueryWrapper<>();
        if (sourceName != null && !sourceName.isEmpty()) {
            wrapper.like(DataSource::getSourceName, sourceName);
        }
        if (sourceType != null && !sourceType.isEmpty()) {
            wrapper.eq(DataSource::getSourceType, sourceType);
        }
        wrapper.orderByDesc(DataSource::getCreateTime);

        Page<DataSource> dataSourcePage = dataSourceService.page(page, wrapper);

        List<DataSourceResponse> records = dataSourcePage.getRecords().stream().map(ds -> {
            DataSourceResponse response = new DataSourceResponse();
            BeanUtils.copyProperties(ds, response);
            response.setPassword(null); // 不返回密码
            return response;
        }).collect(Collectors.toList());

        PageResult<DataSourceResponse> result = new PageResult<>(
                records,
                dataSourcePage.getTotal(),
                dataSourcePage.getCurrent(),
                dataSourcePage.getSize()
        );

        return Result.success(result);
    }

    @PostMapping("/{id}/test")
    @ApiOperation("测试数据源连接")
    public Result<Boolean> testConnection(@ApiParam("数据源ID") @PathVariable Long id) {
        boolean success = dataSourceService.testConnection(id);
        return Result.success(success);
    }

    @PutMapping("/{id}/status")
    @ApiOperation("更新数据源状态")
    public Result<Void> updateStatus(
            @ApiParam("数据源ID") @PathVariable Long id,
            @ApiParam("状态") @RequestParam Integer status) {
        dataSourceService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据源")
    public Result<Void> delete(@ApiParam("数据源ID") @PathVariable Long id) {
        dataSourceService.removeById(id);
        return Result.success();
    }

}
