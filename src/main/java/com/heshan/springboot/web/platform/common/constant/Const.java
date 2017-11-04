package com.heshan.springboot.web.platform.common.constant;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: Const
 * @Description: 常量
 * @author frank
 * @date 2017年8月4日 下午6:07:28
 */
@Component
public class Const {
    public static final String MSG_SUC = "成功";
    public static final String MSG_FAIL = "失败";
    public static final String INVALID_REQUEST = "无效请求";
    public static final String INVALID_PARAM = "请求参数无效";

    /****** ---------------------------------by Frank------------------------------------- ******/
    public static final String THREAD_ID_KEY = "threadId";// 日志线程唯一ID
    public static final String TOKEN_KEY = "token";// 用于表单重复提交
    public static final String AUTHORIZATION_KEY = "Authorization";// 主要用于前端视图层调用
    public static final String ACCESS_TO_KEN_KEY = "access_token";// 主要用于API调用


    /**
     * 合法的图形文件后缀名
     */
    public final static List<String> IMAGE_FILE_EXT = Arrays.asList(new String[] {"jpeg", "jpg", "png", "gif", "JPEG", "JPG", "PNG", "GIF"});

    public final static String FIELD_NAME = "vhsc_upload_file";// 上传文件表单字段名

}
