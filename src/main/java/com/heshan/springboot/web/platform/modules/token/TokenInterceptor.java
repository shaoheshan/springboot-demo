package com.heshan.springboot.web.platform.modules.token;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TokenInterceptor
 * @Description: Token拦截器
 * @author frank
 * @date 2017年5月19日 下午1:17:34
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenProcessor tokenProcessor;
    private Object clock = new Object();

    /*
     * 在方法业务处理完之后，判断是否需要生成token令牌
     */
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AutoToken an = handlerMethod.getMethod().getAnnotation(AutoToken.class);
            if (an != null && an.scope()[0].toString().equals(TokenHandler.CREATE.toString())) {
                Map<String, String> map = tokenProcessor.createToken();
                String tokenHtml = tokenProcessor.buildTokenHtml(map);
                modelAndView.addObject("tokenHtml", tokenHtml);
                req.setAttribute("tokenHtml", tokenHtml);// html ${tokenHtml}
            }
        }
        super.postHandle(req, response, handler, modelAndView);
    }

    /**
     * 拦截方法 验证token
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AutoToken an = handlerMethod.getMethod().getAnnotation(AutoToken.class);
            if (an != null && an.scope()[0].toString().equals(TokenHandler.VALIDATE.toString())) {
                return handleToken(request, response, handler);
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }

    }

    protected boolean handleToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        synchronized (clock) {
            if (!tokenProcessor.validToken(request)) {
                return handleInvalidToken(request, response, handler);
            }
        }
        return handleValidToken(request, response, handler);
    }

    /**
     * 当出现一个非法令牌时调用
     */
    protected boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("code", 502);
        data.put("msg", "请勿重复操作！");
        writeMessageUtf8(response, data);
        return false;
    }

    /**
     * 当发现一个合法令牌时调用.
     */
    protected boolean handleValidToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    private void writeMessageUtf8(HttpServletResponse response, Map<String, Object> json) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            String jsonStr = JSONObject.toJSONString(json);
            response.getWriter().print(jsonStr);
        } catch (Exception e) {
            log.error("消息格式转换异常", e);
        } finally {
            response.getWriter().close();
        }
    }
}
