package com.heshan.springboot.web.platform.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

/**
 * 返回数据
 *
 * @author
 * @email
 * @date 2010年10月27日 下午9:59:27
 */
public class PageResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public PageResult() {
        put("code", HttpStatus.SC_OK);
    }

    public static PageResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    /**
     * 业务逻辑性错误状态码为5xx
     *
     */
    public static PageResult error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static PageResult error(int code, String msg) {
        PageResult r = new PageResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static PageResult ok(String msg) {
        PageResult r = new PageResult();
        r.put("msg", msg);
        return r;
    }

    public static PageResult ok(Map<String, Object> map) {
        PageResult r = new PageResult();
        r.putAll(map);
        return r;
    }

    public static PageResult ok() {
        return new PageResult();
    }


    public PageResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static PageResult setResult(int code, String msg) {
        PageResult r = new PageResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
}
