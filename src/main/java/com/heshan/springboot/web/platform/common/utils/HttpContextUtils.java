package com.heshan.springboot.web.platform.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest req, String key) {
        // 从header中获取token
        String token = req.getHeader(key);
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = req.getParameter(key);
        }
        // 还可以从COOKIE中获取
        if (StringUtils.isBlank(token)) {
            token = CookieUtils.getCookieValue(req, key);
        }
        return token;
    }
}
