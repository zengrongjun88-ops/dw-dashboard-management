package com.dw.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dw.dashboard.dto.request.DashboardCreateRequest;
import com.dw.dashboard.dto.response.DashboardResponse;
import com.dw.dashboard.entity.Dashboard;

/**
 * 报表服务接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
public interface IDashboardService extends IService<Dashboard> {

    /**
     * 创建报表
     *
     * @param request 创建请求
     * @return 报表响应
     */
    DashboardResponse createDashboard(DashboardCreateRequest request);

    /**
     * 发布报表
     *
     * @param dashboardId 报表ID
     */
    void publishDashboard(Long dashboardId);

    /**
     * 下线报表
     *
     * @param dashboardId 报表ID
     */
    void offlineDashboard(Long dashboardId);

}
