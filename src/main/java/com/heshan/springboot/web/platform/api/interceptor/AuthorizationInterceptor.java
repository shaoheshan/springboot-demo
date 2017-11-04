package com.heshan.springboot.web.platform.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heshan.springboot.web.platform.common.utils.HttpContextUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.heshan.springboot.web.platform.api.annotation.AuthIgnore;
import com.heshan.springboot.web.platform.api.entity.TokenEntity;
import com.heshan.springboot.web.platform.api.service.TokenService;
import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.common.exception.BizException;

/**
 * 权限(Token)验证
 *
 * @author
 * @email
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        // 如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        String token = HttpContextUtils.getRequestToken(request, Const.ACCESS_TO_KEN_KEY);
        if (StringUtils.isBlank(token)) {
            throw new BizException("access_token不能为空");
        }

        // 查询token信息
        // TODO 性能问题，可考虑redis缓存，由于医院端是单机系统，暂不作过多设计
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new BizException("access_token失效，请重新登录");
        }

        // 设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
