package com.heshan.springboot.web.platform.common.utils;

/**
 * Redis所有Keys
 *
 * @author
 * @email
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
