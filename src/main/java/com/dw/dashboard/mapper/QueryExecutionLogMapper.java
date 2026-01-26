package com.dw.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dw.dashboard.entity.QueryExecutionLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 查询执行日志Mapper接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Mapper
public interface QueryExecutionLogMapper extends BaseMapper<QueryExecutionLog> {

}
