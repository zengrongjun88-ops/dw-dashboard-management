package com.dw.dashboard.controller;

import com.dw.dashboard.common.Result;
import com.dw.dashboard.dto.request.QueryExecuteRequest;
import com.dw.dashboard.dto.response.QueryResultResponse;
import com.dw.dashboard.service.IDashboardExecuteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 报表执行控制器
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@RestController
@RequestMapping("/dashboard/execute")
@Api(tags = "报表执行")
public class DashboardExecuteController {

    @Autowired
    private IDashboardExecuteService dashboardExecuteService;

    @PostMapping("/query")
    @ApiOperation("执行查询")
    public Result<QueryResultResponse> executeQuery(@Validated @RequestBody QueryExecuteRequest request) {
        QueryResultResponse response = dashboardExecuteService.executeQuery(request);
        return Result.success(response);
    }

    @DeleteMapping("/cache/{queryId}")
    @ApiOperation("清除查询缓存")
    public Result<Void> clearCache(@ApiParam("查询配置ID") @PathVariable Long queryId) {
        dashboardExecuteService.clearQueryCache(queryId);
        return Result.success();
    }

}
