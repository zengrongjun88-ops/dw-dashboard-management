package com.dw.dashboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.dto.request.DashboardCreateRequest;
import com.dw.dashboard.dto.response.DashboardResponse;
import com.dw.dashboard.entity.Dashboard;
import com.dw.dashboard.enums.DashboardStatus;
import com.dw.dashboard.exception.BusinessException;
import com.dw.dashboard.mapper.DashboardMapper;
import com.dw.dashboard.service.IDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报表服务实现
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Service
public class DashboardServiceImpl extends ServiceImpl<DashboardMapper, Dashboard> implements IDashboardService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DashboardResponse createDashboard(DashboardCreateRequest request) {
        // 1. 检查报表编码是否已存在
        LambdaQueryWrapper<Dashboard> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dashboard::getDashboardCode, request.getDashboardCode());
        Dashboard existDashboard = getOne(wrapper);
        if (existDashboard != null) {
            throw new BusinessException(ResultCode.DATA_EXISTS, "报表编码已存在");
        }

        // 2. 创建报表
        Dashboard dashboard = Dashboard.builder()
                .dashboardName(request.getDashboardName())
                .dashboardCode(request.getDashboardCode())
                .category(request.getCategory())
                .description(request.getDescription())
                .status(DashboardStatus.DRAFT.getCode())
                .build();

        save(dashboard);

        // 3. 返回响应
        DashboardResponse response = new DashboardResponse();
        BeanUtils.copyProperties(dashboard, response);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishDashboard(Long dashboardId) {
        Dashboard dashboard = getById(dashboardId);
        if (dashboard == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXISTS, "报表不存在");
        }
        dashboard.setStatus(DashboardStatus.PUBLISHED.getCode());
        updateById(dashboard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offlineDashboard(Long dashboardId) {
        Dashboard dashboard = getById(dashboardId);
        if (dashboard == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXISTS, "报表不存在");
        }
        dashboard.setStatus(DashboardStatus.OFFLINE.getCode());
        updateById(dashboard);
    }

}
