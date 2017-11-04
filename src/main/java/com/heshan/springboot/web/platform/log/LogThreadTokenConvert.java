package com.heshan.springboot.web.platform.log;

import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.support.LocalThreadContext;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Random;

/**
 * @author Frank
 * @version V2.0
 * @Title: TokenConvert.java
 * @Package com.heshan.springboot.web.platform.log
 * @Description: 当前请求线程调用链token转换器
 * @Company www.flyheze.top
 * @date 2017年8月11日 下午7:45:31
 */
public class LogThreadTokenConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        return LogThreadTokenConvert.getThreadToken();
    }

    public static String getThreadToken() {
        String token = (String) LocalThreadContext.getContext().get(Const.THREAD_ID_KEY);
        if (token == null) {
            return genThreadToken();
        }
        return token;
    }

    public static void clearToken() {
        LocalThreadContext.getContext().remove(Const.THREAD_ID_KEY);
    }

    private static String genThreadToken() {
        long time = System.currentTimeMillis();
        long random = new Random().nextInt(899) + 100;
        long threadId = Thread.currentThread().getId();
        String token = time + "_" + random + "_" + threadId;
        LocalThreadContext.getContext().put(Const.THREAD_ID_KEY, token);
        return token;
    }

    public static void main(String[] args) {
        System.out.println(genThreadToken());
    }
}