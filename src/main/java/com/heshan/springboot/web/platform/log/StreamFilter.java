package com.heshan.springboot.web.platform.log;


import com.alibaba.fastjson.JSON;
import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.common.utils.HttpContextUtils;
import com.heshan.springboot.web.platform.common.utils.SpringHelper;
import com.heshan.springboot.web.platform.config.VhscProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * 使用ContentCachingRequestWrapper来包装HttpServletRequest，
 * 使用ContentCachingResponseWrapper来包装HttpServletResponse，
 *
 * @author
 * @email
 * @date 2017-04-01 10:20
 */
@Slf4j
public class StreamFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            MDC.put(Const.THREAD_ID_KEY, genThreadToken());
            HttpServletRequest req = (HttpServletRequest) request;
            String contentType = request.getContentType();
            HttpServletResponse resp = (HttpServletResponse) response;
            String method = req.getMethod();

            //放行没有Authorization（静态资源）认证的请求 && 非前置服务API调用
            boolean hasApiUri = req.getRequestURI().startsWith("/api/");
            boolean auth = StringUtils.isBlank(HttpContextUtils.getRequestToken(req, Const.AUTHORIZATION_KEY));
            if ((!(request instanceof HttpServletRequest) || auth) && !hasApiUri) {
                chain.doFilter(request, response);
                return;
            }
            //上传文件因二进制请求体过大，放行
            if ((contentType != null) && (contentType.startsWith("multipart/form-data")) && ("POST".equalsIgnoreCase(method))) {
                log.info("==> 当前请求为文件上传，不作调用链日志收集");
                chain.doFilter(request, response);
                return;
            }

            Date startTime = new Date();
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(resp);
            try {
                chain.doFilter(requestWrapper, responseWrapper);
            } finally {
                traceDetil(startTime, requestWrapper, responseWrapper);
            }
        } finally {
            MDC.remove(Const.THREAD_ID_KEY);
        }
    }

    /**
     * 收集请求链基本追踪信息
     * <p>
     * Nginx Tomcat 默认的maxHttpHeaderSize设置好像是4K，如因json体过大，
     * 需更改<Connector port="8080" maxHttpHeaderSize="8192" />
     * </p>
     *
     * @param startTime       请求开始时间
     * @param requestWrapper  转换后的request
     * @param responseWrapper 转换后的response
     * @throws IOException
     */
    private void traceDetil(Date startTime, ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) throws IOException {
        VhscProperties vhscProperties = (VhscProperties) SpringHelper.getBean("vhscProperties");
        String requestBody = requestWrapper.getQueryString();
        if (StringUtils.isEmpty(requestBody)) {
            requestBody = new String(requestWrapper.getContentAsByteArray());
        }
        String responseBody = new String(responseWrapper.getContentAsByteArray());
        LogPersistEntity logInfo = LogPersistEntity.build(requestWrapper, startTime, requestBody, responseBody);
        boolean isNotice = logInfo.getWasteTime() > vhscProperties.getRespNoticeTime();
        String json = logInfo.toJson();

        log.info("[日志归档] - {}", json);
        if (isNotice) {
            log.warn("[性能告警] - {}", json);
        }
        BaseLogEntity webLog = BaseLogEntity.builder(logInfo);
        responseWrapper.setHeader("X-Slave", JSON.toJSONString(webLog));
        responseWrapper.copyBodyToResponse();
    }

    @Override
    public void destroy() {
    }

    private static String genThreadToken() {
        long time = System.currentTimeMillis();
        long random = new Random().nextInt(899) + 100;
        long threadId = Thread.currentThread().getId();
        String token = time + "-" + random + "-" + threadId;
        return token;
    }

}
