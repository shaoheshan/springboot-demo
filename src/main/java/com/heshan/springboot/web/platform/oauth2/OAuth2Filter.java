package com.heshan.springboot.web.platform.oauth2;


import com.alibaba.fastjson.JSONObject;
import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.common.utils.HttpContextUtils;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 * @author
 * @email
 * @date 2012-05-20 13:00
 */
public class OAuth2Filter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token
        String token = HttpContextUtils.getRequestToken((HttpServletRequest) request, Const.AUTHORIZATION_KEY);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求token，如果token不存在，直接返回401
        String token = HttpContextUtils.getRequestToken((HttpServletRequest) request, Const.AUTHORIZATION_KEY);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            String json = JSONObject.toJSONString(PageResult.error(HttpStatus.SC_UNAUTHORIZED, "无法获取Authorization"));
            httpResponse.getWriter().print(json);
            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            // 处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            PageResult r = PageResult.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = JSONObject.toJSONString(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return false;
    }

}
