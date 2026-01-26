package com.dw.dashboard.exception;

import com.dw.dashboard.common.ResultCode;

/**
 * SQL执行异常
 *
 * @author DW Team
 * @since 2026-01-26
 */
public class SqlExecutionException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public SqlExecutionException(String message) {
        super(ResultCode.SQL_EXECUTION_ERROR.getCode(), message);
    }

    public SqlExecutionException(String message, Throwable cause) {
        super(ResultCode.SQL_EXECUTION_ERROR.getCode(), message);
        initCause(cause);
    }

}
