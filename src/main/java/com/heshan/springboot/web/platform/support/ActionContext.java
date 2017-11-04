package com.heshan.springboot.web.platform.support;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前请求线程应用上下文
 * 最终合并LocalThreadContext类
 *
 * @author Frank
 * @version 2.0
 * @date 2017/9/15 22:41
 */
public class ActionContext {
    private static ThreadLocal<Map<String, Object>> LOCAL_CONTEXT = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };
}
