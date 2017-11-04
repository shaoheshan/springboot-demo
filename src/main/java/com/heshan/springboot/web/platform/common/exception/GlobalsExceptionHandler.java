package com.heshan.springboot.web.platform.common.exception;

import com.heshan.springboot.web.platform.common.web.PlatformDict;
import com.heshan.springboot.web.platform.common.web.ViewResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author
 * @email
 * @date 2016年10月27日 下午10:16:19
 */
@Slf4j
@RestControllerAdvice
public class GlobalsExceptionHandler {
    /**
     * 自定义异常
     */
    @ExceptionHandler(BizException.class)
    public ViewResult handleBizException(BizException e) {
        return ViewResult.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ViewResult handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return ViewResult.error(PlatformDict.SYS_SERVER_ERROR_DBEXIST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ViewResult handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return ViewResult.error(PlatformDict.SYS_SERVER_ERROR_UNAUTH);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ViewResult httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ViewResult.error(HttpStatus.SC_METHOD_NOT_ALLOWED + "", "不支持当前请求方法:" + e.getMethod());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ViewResult httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(PlatformDict.SYS_SERVER_ERROR_BODY.getMsg());
        log.error(e.getMessage(), e);
        return ViewResult.error(PlatformDict.SYS_SERVER_ERROR_BODY);
    }

    @ExceptionHandler(NullPointerException.class)
    public ViewResult nullPointerException(NullPointerException e) {
        log.error(PlatformDict.SYS_SERVER_ERROR_NULL.getMsg());
        log.error(e.getMessage(), e);
        return ViewResult.error(PlatformDict.SYS_SERVER_ERROR_NULL);
    }

    @ExceptionHandler(TooManyResultsException.class)
    public ViewResult tooManyResultsException(TooManyResultsException e) {
        log.error(PlatformDict.SYS_SERVER_ERROR_TooMany.getMsg());
        log.error(e.getMessage(), e);
        return ViewResult.error(PlatformDict.SYS_SERVER_ERROR_TooMany);
    }

    @ExceptionHandler(UncategorizedSQLException.class)
    public ViewResult sqlException(UncategorizedSQLException e) {
        log.error(PlatformDict.SYS_SERVER_ERROR_SQL.getMsg());
        log.error(e.getMessage(), e);
        return ViewResult.error(PlatformDict.SYS_SERVER_ERROR_SQL);
    }

    @ExceptionHandler(Exception.class)
    public ViewResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ViewResult.error(PlatformDict.SYS_SERVER_ERROR_UNKNOW);
    }
}
