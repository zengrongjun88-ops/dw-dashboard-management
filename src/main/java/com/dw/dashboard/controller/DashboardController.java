package com.dw.dashboard.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dw.dashboard.common.PageResult;
import com.dw.dashboard.common.Result;
import com.dw.dashboard.dto.request.DashboardCreateRequest;
import com.dw.dashboard.dto.response.DashboardResponse;
import com.dw.dashboard.entity.Dashboard;
import com.dw.dashboard.service.IDashboardService;
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
 * 报表控制器
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@RestController
@RequestMapping("/dashboard")
@Api(tags = "报表管理")
public class DashboardController {

    @Autowired
    private IDashboardService dashboardService;

    @PostMapping
    @ApiOperation("创建报表")
    public Result<DashboardResponse> create(@Validated @RequestBody DashboardCreateRequest request) {
        DashboardResponse response = dashboardService.createDashboard(request);
        return Result.success(response);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询报表")
    public Result<DashboardResponse> getById(@ApiParam("报表ID") @PathVariable Long id) {
        Dashboard dashboard = dashboardService.getById(id);
        if (dashboard == null) {
            return Result.error("报表不存在");
        }
        DashboardResponse response = new DashboardResponse();
        BeanUtils.copyProperties(dashboard, response);
        return Result.success(response);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询报表")
    public Result<PageResult<DashboardResponse>> page(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Long current,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Long size,
            @ApiParam("报表名称") @RequestParam(required = false) String dashboardName,
            @ApiParam("报表分类") @RequestParam(required = false) String category,
            @ApiParam("状态") @RequestParam(required = false) Integer status) {

        Page<Dashboard> page = new Page<>(current, size);
        LambdaQueryWrapper<Dashboard> wrapper = new LambdaQueryWrapper<>();
        if (dashboardName != null && !dashboardName.isEmpty()) {
            wrapper.like(Dashboard::getDashboardName, dashboardName);
        }
        if (category != null && !category.isEmpty()) {
            wrapper.eq(Dashboard::getCategory, category);
        }
        if (status != null) {
            wrapper.eq(Dashboard::getStatus, status);
        }
        wrapper.orderByDesc(Dashboard::getCreateTime);

        Page<Dashboard> dashboardPage = dashboardService.page(page, wrapper);

        List<DashboardResponse> records = dashboardPage.getRecords().stream().map(dashboard -> {
            DashboardResponse response = new DashboardResponse();
            BeanUtils.copyProperties(dashboard, response);
            return response;
        }).collect(Collectors.toList());

        PageResult<DashboardResponse> result = new PageResult<>(
                records,
                dashboardPage.getTotal(),
                dashboardPage.getCurrent(),
                dashboardPage.getSize()
        );

        return Result.success(result);
    }

    @PutMapping("/{id}/publish")
    @ApiOperation("发布报表")
    public Result<Void> publish(@ApiParam("报表ID") @PathVariable Long id) {
        dashboardService.publishDashboard(id);
        return Result.success();
    }

    @PutMapping("/{id}/offline")
    @ApiOperation("下线报表")
    public Result<Void> offline(@ApiParam("报表ID") @PathVariable Long id) {
        dashboardService.offlineDashboard(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除报表")
    public Result<Void> delete(@ApiParam("报表ID") @PathVariable Long id) {
        dashboardService.removeById(id);
        return Result.success();
    }

}
