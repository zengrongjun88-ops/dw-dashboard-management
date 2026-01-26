package com.dw.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dw.dashboard.entity.Dashboard;
import org.apache.ibatis.annotations.Mapper;

/**
 * 报表定义Mapper接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Mapper
public interface DashboardMapper extends BaseMapper<Dashboard> {

}
