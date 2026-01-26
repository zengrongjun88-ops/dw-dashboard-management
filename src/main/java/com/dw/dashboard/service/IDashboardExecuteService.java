package com.dw.dashboard.service;

import com.dw.dashboard.dto.request.QueryExecuteRequest;
import com.dw.dashboard.dto.response.QueryResultResponse;

/**
 * 报表执行服务接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
public interface IDashboardExecuteService {

    /**
     * 执行查询
     *
     * @param request 执行请求
     * @return 查询结果
     */
    QueryResultResponse executeQuery(QueryExecuteRequest request);

    /**
     * 清除查询缓存
     *
     * @param queryId 查询配置ID
     */
    void clearQueryCache(Long queryId);

}
