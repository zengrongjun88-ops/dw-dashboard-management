package com.dw.dashboard.exception;

import com.dw.dashboard.common.Result;
import com.dw.dashboard.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 数据源异常
     */
    @ExceptionHandler(DataSourceException.class)
    public Result<Void> handleDataSourceException(DataSourceException e) {
        log.error("数据源异常：{}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * SQL执行异常
     */
    @ExceptionHandler(SqlExecutionException.class)
    public Result<Void> handleSqlExecutionException(SqlExecutionException e) {
        log.error("SQL执行异常：{}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(PermissionException.class)
    public Result<Void> handlePermissionException(PermissionException e) {
        log.error("权限异常：{}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常（@Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数校验异常：{}", message);
        return Result.error(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数绑定异常：{}", message);
        return Result.error(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("约束违反异常：{}", message);
        return Result.error(ResultCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常：{}", e.getMessage(), e);
        return Result.error(ResultCode.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<Void> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常", e);
        return Result.error(ResultCode.ERROR.getCode(), "系统内部错误");
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return Result.error(ResultCode.ERROR.getCode(), "系统内部错误：" + e.getMessage());
    }

}
