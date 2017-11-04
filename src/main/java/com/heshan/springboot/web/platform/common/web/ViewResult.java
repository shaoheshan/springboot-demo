package com.heshan.springboot.web.platform.common.web;

import com.heshan.springboot.web.platform.common.validator.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据请求完返回给视图的数据结果模型
 *
 * @author Frank
 * @version 2.0
 * @date 2017/9/9 21:30
 */
@Slf4j
public class ViewResult extends HashMap<String, Object> {
    public static final String DATA = "data";//数据体
    public static final String CODE = "code";//状态码
    public static final String MSG = "msg";//状态码对应的消息值
    public static final String ERRORS = "errors";//错误结果集
    public static final String SUCCESS = "success";//是否成功/失败

    private static Map<String, Object> data = new HashMap<>();
    private static Map<String, Object> errors = new HashMap<>();


    public ViewResult() {
        put(this.SUCCESS, true);
    }

    public ViewResult(Boolean hasSuccess) {
        put(this.SUCCESS, hasSuccess);
    }


    public static ViewResult ok() {
        return new ViewResult();
    }

    public static ViewResult ok(Map<String, Object> map) {
        ViewResult r = new ViewResult();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            r.put(entry.getKey(), entry.getValue());
        }
        return r;
    }

    /**
     * 条件约束
     * 1、当success:true,则put的key不能为msg||code.
     * 2、当success:true,则put对象只能放在data集合下
     * 3、当success:false,则调用put动作时key只能为msg||code||success
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public ViewResult put(String key, Object value) {
        Assert.hasBlank("value is null", key);
        Object success = super.get(this.SUCCESS);
        if (success == null) {
            super.put(key, value);
            return this;
        }

        if ((Boolean) success) {
            if (this.DATA.equals(key)) {
                data.clear();
                super.put(key, value);
            } else {
                data.put(key, value);
                super.put(this.DATA, data);
            }
        } else {
            if (this.ERRORS.equals(key)) {
                super.put(key, value);
            } else {
                errors.put(key, value);
                super.putAll(errors);
            }

        }
        return this;
    }

    public static ViewResult setData(Object data) {
        Assert.hasBlank(data, "result data is null");
        return new ViewResult().put(ViewResult.DATA, data);
    }


    public static ViewResult error(PlatformDict platformDict) {
        Assert.hasBlank(platformDict, "result error is null");
        return new ViewResult(false).put(ViewResult.MSG, platformDict.getMsg()).put(ViewResult.CODE, platformDict.getCode());
    }

    public static ViewResult error(String code, String msg) {
        return new ViewResult(false).put(ViewResult.MSG, msg).put(ViewResult.CODE, code);
    }

    public ViewResult errorData(Object errors) {
        Assert.hasBlank(errors, "result errors is null");
        this.put(ViewResult.ERRORS, errors);
        return this;
    }

}
