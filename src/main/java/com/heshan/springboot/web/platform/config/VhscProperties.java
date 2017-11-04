package com.heshan.springboot.web.platform.config;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @ClassName: VhscProperties
 * @Description: 系统平台层配置
 * @date 2017年7月30日 下午7:02:50
 */
@Component
@ConfigurationProperties(prefix = VhscProperties.PREFIX)
public class VhscProperties {
    public static final String PREFIX = "vhsc";

    private Boolean kaptchaOpen = false;
    private String allowedOrigins = "*";
    private String fileUploadPath = "upload";// 文件上传存储地址,可以是绝对路径也可是相对路径
    private Long fileUploadMax = Long.valueOf(1024 * 1024 * 5);// #单位B，默认5M(1024*1024*5)
    private Integer authorizationExpireTime = 3600 * 24; // authorization 失效时间（默认为24小时 单位：秒）

    /******************-----------start配置日志收集项---------***************/
    @Value("${vhsc.log.respNoticeTime}")
    private Short respNoticeTime = 1000;// 超过1000毫秒的调用链则日志告警
    @Value("${vhsc.log.hasHeaders}")
    private Boolean hasHeaders = false;//是否收集请求头信息
    @Value("${vhsc.log.hasParams}")
    private Boolean hasParams = true;//是否收集请求参数信息
    @Value("${vhsc.log.hasResult}")
    private Boolean hasResult = false;//是否收集返回值信息

    /******************-----------end配置日志收集项---------*****************/

    public String getFileUploadPath() {
        // 如果没有写文件上传路径,保存到临时目录
        if (StringUtils.isEmpty(fileUploadPath)) {
            return System.getProperty("java.io.tmpdir");
        } else {
            // 判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            return fileUploadPath;
        }
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Boolean getKaptchaOpen() {
        return kaptchaOpen;
    }

    public void setKaptchaOpen(Boolean kaptchaOpen) {
        this.kaptchaOpen = kaptchaOpen;
    }

    public Integer getAuthorizationExpireTime() {
        return authorizationExpireTime;
    }

    public void setAuthorizationExpireTime(Integer authorizationExpireTime) {
        this.authorizationExpireTime = authorizationExpireTime;
    }

    public String getAllowedOrigins() {
        return allowedOrigins;
    }

    public Short getRespNoticeTime() {
        return respNoticeTime;
    }

    public void setRespNoticeTime(Short respNoticeTime) {
        this.respNoticeTime = respNoticeTime;
    }

    public void setAllowedOrigins(String allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public Long getFileUploadMax() {
        return fileUploadMax;
    }

    public Boolean getHasHeaders() {
        return hasHeaders;
    }

    public void setHasHeaders(Boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
    }

    public Boolean getHasParams() {
        return hasParams;
    }

    public void setHasParams(Boolean hasParams) {
        this.hasParams = hasParams;
    }

    public Boolean getHasResult() {
        return hasResult;
    }

    public void setHasResult(Boolean hasResult) {
        this.hasResult = hasResult;
    }

    public void setFileUploadMax(Long fileUploadMax) {
        this.fileUploadMax = fileUploadMax;
    }

    public void setFileUploadMax(long fileUploadMax) {
        this.fileUploadMax = fileUploadMax;
    }
}
