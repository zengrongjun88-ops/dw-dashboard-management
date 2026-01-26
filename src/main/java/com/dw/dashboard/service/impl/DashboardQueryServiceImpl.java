package com.dw.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.dashboard.entity.DashboardQuery;
import com.dw.dashboard.mapper.DashboardQueryMapper;
import com.dw.dashboard.service.IDashboardQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 报表查询配置服务实现
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Service
public class DashboardQueryServiceImpl extends ServiceImpl<DashboardQueryMapper, DashboardQuery> implements IDashboardQueryService {

}
