package com.dw.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dw.dashboard.dto.request.DataSourceCreateRequest;
import com.dw.dashboard.dto.response.DataSourceResponse;
import com.dw.dashboard.entity.DataSource;

/**
 * 数据源服务接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
public interface IDataSourceService extends IService<DataSource> {

    /**
     * 创建数据源
     *
     * @param request 创建请求
     * @return 数据源响应
     */
    DataSourceResponse createDataSource(DataSourceCreateRequest request);

    /**
     * 测试数据源连接
     *
     * @param dataSourceId 数据源ID
     * @return true-连接成功，false-连接失败
     */
    boolean testConnection(Long dataSourceId);

    /**
     * 更新数据源状态
     *
     * @param dataSourceId 数据源ID
     * @param status 状态
     */
    void updateStatus(Long dataSourceId, Integer status);

}
