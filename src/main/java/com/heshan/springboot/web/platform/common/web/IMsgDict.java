package com.heshan.springboot.web.platform.common.web;

/**
 * 规范消息字典，异常信息使用枚举类来定义必须实现此接口
 *
 * @author Frank
 * @version 2.0
 * @date 2017/9/16 22:29
 */
public interface IMsgDict {
    String getCode();

    String getMsg();
}
