package com.dw.dashboard.exception;

import com.dw.dashboard.common.ResultCode;

/**
 * 数据源异常
 *
 * @author DW Team
 * @since 2026-01-26
 */
public class DataSourceException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public DataSourceException(String message) {
        super(ResultCode.DATASOURCE_ERROR.getCode(), message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(ResultCode.DATASOURCE_ERROR.getCode(), message);
        initCause(cause);
    }

}
