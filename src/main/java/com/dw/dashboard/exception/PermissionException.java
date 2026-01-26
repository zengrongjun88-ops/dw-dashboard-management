package com.dw.dashboard.exception;

import com.dw.dashboard.common.ResultCode;

/**
 * 权限异常
 *
 * @author DW Team
 * @since 2026-01-26
 */
public class PermissionException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public PermissionException(String message) {
        super(ResultCode.PERMISSION_DENIED.getCode(), message);
    }

    public PermissionException() {
        super(ResultCode.PERMISSION_DENIED);
    }

}
