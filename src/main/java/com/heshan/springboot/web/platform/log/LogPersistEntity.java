package com.heshan.springboot.web.platform.log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.common.utils.IPUtils;
import com.heshan.springboot.web.platform.common.utils.ShiroUtils;
import com.heshan.springboot.web.platform.config.VhscProperties;
import com.heshan.springboot.web.platform.common.utils.SpringHelper;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求日志信息收集(LogBack使用)
 *
 * @author Frank
 * @version 2.0
 * @date 2017/9/2 22:45
 */
public class LogPersistEntity extends BaseLogEntity implements Serializable {
    private String host;//主机
    private String scheme;//协议
    private String path;//请求路径
    private Object result;//返回值
    private String method;//请求方法
    private Object params;//请求参数
    private String clientIp;//客户端IP
    private Map<String, String> headers;//请求头


    public String toJson() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 生成日志对象
     *
     * @param req
     */
    public static LogPersistEntity build(HttpServletRequest req, Date startTime, String requestBody, String responseBody) {
        LogPersistEntity info = new LogPersistEntity();
//        info.setThreadToken(LogThreadTokenConvert.getThreadToken());
        info.setThreadToken(MDC.get(Const.THREAD_ID_KEY));
        info.scheme = req.getScheme();
        info.host = req.getServerName();
        info.setPort(req.getServerPort());
        info.path = req.getRequestURI();
        info.method = req.getMethod();
        info.clientIp = IPUtils.getClientIp(req);
        info.setStartTime(startTime);
        info.setEndTime(new Date());
        info.setServerIp(IPUtils.getServerIp());

        try {
            SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
            if (sysUserEntity != null) {
                info.setRemoteUser(sysUserEntity.getUserId() + ":" + sysUserEntity.getUsername());
            }
        } catch (Exception e) {
            info.setRemoteUser(req.getRemoteUser());
        }

        VhscProperties vp = (VhscProperties) SpringHelper.getBean("vhscProperties");
        if (vp.getHasHeaders()) {
            info.headers = getHeaderMap(req);
        }
        if (vp.getHasParams()) {
            try {
                info.params = JSONObject.parseObject(requestBody);
            } catch (JSONException e) {
                info.params = requestBody;
            }
        }
        if (vp.getHasResult()) {
            try {
                info.result = JSONObject.parseObject(responseBody);
            } catch (JSONException e) {
                info.result = responseBody;
            }
        }
        return info;
    }

    public static Map<String, String> getHeaderMap(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();
        Enumeration enu = req.getHeaderNames();//取得全部头信息
        while (enu.hasMoreElements()) {//以此取出头Name
            String headerName = (String) enu.nextElement();
            String headerValue = req.getHeader(headerName);//取出头信息Value
            map.put(headerName, headerValue);
        }
        return map;
    }


    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


}
