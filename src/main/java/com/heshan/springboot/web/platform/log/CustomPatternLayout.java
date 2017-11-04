package com.heshan.springboot.web.platform.log;

import ch.qos.logback.classic.PatternLayout;

/**
 * @author
 * @ClassName: CustomPatternLayout
 * @Description: 日志装饰类，加入全局调用链唯一号
 * @date 2014年01月19日 下午8:10:06
 */
public class CustomPatternLayout extends PatternLayout {
    static {
        defaultConverterMap.put("token", LogThreadTokenConvert.class.getName());
    }
}