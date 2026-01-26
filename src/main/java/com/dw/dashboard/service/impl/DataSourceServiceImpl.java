package com.dw.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.dto.request.DataSourceCreateRequest;
import com.dw.dashboard.dto.response.DataSourceResponse;
import com.dw.dashboard.entity.DataSource;
import com.dw.dashboard.enums.DataSourceType;
import com.dw.dashboard.exception.BusinessException;
import com.dw.dashboard.exception.DataSourceException;
import com.dw.dashboard.mapper.DataSourceMapper;
import com.dw.dashboard.service.IDataSourceService;
import com.dw.dashboard.util.EncryptUtil;
import com.dw.dashboard.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据源服务实现
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Service
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements IDataSourceService {

    @Autowired
    private EncryptUtil encryptUtil;

    @Autowired
    private JdbcUtil jdbcUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataSourceResponse createDataSource(DataSourceCreateRequest request) {
        // 1. 验证数据源类型
        DataSourceType type = DataSourceType.getByCode(request.getSourceType());
        if (type == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "不支持的数据源类型");
        }

        // 2. 加密密码
        String encryptedPassword = encryptUtil.encrypt(request.getPassword());

        // 3. 创建数据源
        DataSource dataSource = DataSource.builder()
                .sourceName(request.getSourceName())
                .sourceType(request.getSourceType())
                .host(request.getHost())
                .port(request.getPort())
                .databaseName(request.getDatabaseName())
                .username(request.getUsername())
                .password(encryptedPassword)
                .connectionParams(request.getConnectionParams())
                .description(request.getDescription())
                .status(1)
                .build();

        save(dataSource);

        // 4. 返回响应
        DataSourceResponse response = new DataSourceResponse();
        BeanUtils.copyProperties(dataSource, response);
        response.setPassword(null); // 不返回密码
        return response;
    }

    @Override
    public boolean testConnection(Long dataSourceId) {
        DataSource dataSource = getById(dataSourceId);
        if (dataSource == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXISTS, "数据源不存在");
        }

        try {
            return jdbcUtil.testConnection(dataSource);
        } catch (Exception e) {
            log.error("数据源连接测试失败", e);
            throw new DataSourceException("数据源连接测试失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long dataSourceId, Integer status) {
        DataSource dataSource = getById(dataSourceId);
        if (dataSource == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXISTS, "数据源不存在");
        }
        dataSource.setStatus(status);
        updateById(dataSource);
    }

}
