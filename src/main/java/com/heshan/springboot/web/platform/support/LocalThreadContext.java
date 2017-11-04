package com.heshan.springboot.web.platform.support;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: LocalThreadContext.java
 * @Package com.heshan.springboot.web.platform.log
 * @Description: 本地线程变量管理
 * @author Frank
 * @Company www.flyheze.top
 * @date 2010年02月29日 下午8:24:11
 * @version V2.0
 */
public class LocalThreadContext {
    private static LocalThreadContext CONTEXT = new LocalThreadContext();

    private static ThreadLocal<Map<String, Object>> LOCAL_CONTEXT = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    private LocalThreadContext() {
    }

    public static LocalThreadContext getContext() {
        return CONTEXT;
    }

    public boolean put(String key, Object value) {
        LOCAL_CONTEXT.get().put(key, value);
        return true;
    }

    public Object get(String key) {
        return LOCAL_CONTEXT.get().get(key);
    }

    public Object remove(String key) {
        return LOCAL_CONTEXT.get().remove(key);
    }

    public void clear() {
        LOCAL_CONTEXT.get().clear();
    }

}
