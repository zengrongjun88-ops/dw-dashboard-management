package com.dw.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.dashboard.entity.QueryExecutionLog;
import com.dw.dashboard.mapper.QueryExecutionLogMapper;
import com.dw.dashboard.service.IQueryExecutionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 查询执行日志服务实现
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Service
public class QueryExecutionLogServiceImpl extends ServiceImpl<QueryExecutionLogMapper, QueryExecutionLog> implements IQueryExecutionLogService {

}
