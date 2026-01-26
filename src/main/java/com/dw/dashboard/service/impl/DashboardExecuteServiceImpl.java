package com.dw.dashboard.service.impl;

import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.common.constants.CacheConstants;
import com.dw.dashboard.dto.request.QueryExecuteRequest;
import com.dw.dashboard.dto.response.QueryResultResponse;
import com.dw.dashboard.entity.DataSource;
import com.dw.dashboard.entity.DashboardQuery;
import com.dw.dashboard.entity.QueryExecutionLog;
import com.dw.dashboard.exception.BusinessException;
import com.dw.dashboard.service.IDataSourceService;
import com.dw.dashboard.service.IDashboardExecuteService;
import com.dw.dashboard.service.IQueryExecutionLogService;
import com.dw.dashboard.service.IDashboardQueryService;
import com.dw.dashboard.util.JdbcUtil;
import com.dw.dashboard.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 报表执行服务实现
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Service
public class DashboardExecuteServiceImpl implements IDashboardExecuteService {

    @Autowired
    private IDashboardQueryService dashboardQueryService;

    @Autowired
    private IDataSourceService dataSourceService;

    @Autowired
    private IQueryExecutionLogService queryExecutionLogService;

    @Autowired
    private JdbcUtil jdbcUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public QueryResultResponse executeQuery(QueryExecuteRequest request) {
        long startTime = System.currentTimeMillis();

        // 1. 获取查询配置
        DashboardQuery query = dashboardQueryService.getById(request.getQueryId());
        if (query == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXISTS, "查询配置不存在");
        }

        // 2. 检查缓存
        String cacheKey = CacheConstants.QUERY_RESULT_PREFIX + request.getQueryId();
        if (request.getUseCache() && query.getCacheEnabled() == 1) {
            Object cachedResult = redisUtil.get(cacheKey);
            if (cachedResult != null) {
                log.info("从缓存获取查询结果: queryId={}", request.getQueryId());
                QueryResultResponse response = (QueryResultResponse) cachedResult;
                response.setFromCache(true);
                return response;
            }
        }

        // 3. 获取数据源
        DataSource dataSource = dataSourceService.getById(query.getDataSourceId());
        if (dataSource == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXISTS, "数据源不存在");
        }

        // 4. 执行查询
        List<Map<String, Object>> data;
        try {
            data = jdbcUtil.executeQuery(dataSource, query.getQuerySql(), request.getParams());
        } catch (Exception e) {
            log.error("查询执行失败", e);
            // 记录失败日志
            saveExecutionLog(query, dataSource, 0, 0, e.getMessage());
            throw e;
        }

        long endTime = System.currentTimeMillis();
        int executeTime = (int) (endTime - startTime);

        // 5. 构建响应
        QueryResultResponse response = QueryResultResponse.builder()
                .queryId(query.getId())
                .queryName(query.getQueryName())
                .executeSql(query.getQuerySql())
                .executeTime(executeTime)
                .resultRows(data.size())
                .data(data)
                .fromCache(false)
                .build();

        // 6. 缓存结果
        if (query.getCacheEnabled() == 1) {
            redisUtil.set(cacheKey, response, query.getCacheExpire());
        }

        // 7. 记录执行日志
        saveExecutionLog(query, dataSource, executeTime, data.size(), null);

        return response;
    }

    @Override
    public void clearQueryCache(Long queryId) {
        String cacheKey = CacheConstants.QUERY_RESULT_PREFIX + queryId;
        redisUtil.delete(cacheKey);
        log.info("清除查询缓存: queryId={}", queryId);
    }

    /**
     * 保存执行日志
     */
    private void saveExecutionLog(DashboardQuery query, DataSource dataSource,
                                   int executeTime, int resultRows, String errorMessage) {
        QueryExecutionLog log = QueryExecutionLog.builder()
                .dashboardId(query.getDashboardId())
                .queryId(query.getId())
                .dataSourceId(dataSource.getId())
                .executeSql(query.getQuerySql())
                .executeTime(executeTime)
                .resultRows(resultRows)
                .status(errorMessage == null ? 1 : 0)
                .errorMessage(errorMessage)
                .executeUser("system") // TODO: 从上下文获取当前用户
                .executeIp("127.0.0.1") // TODO: 从请求获取IP
                .build();

        queryExecutionLogService.save(log);
    }

}
