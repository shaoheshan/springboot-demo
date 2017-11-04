package com.heshan.springboot.web.platform.common.web;

/**
 * @author Frank
 * @version 2.0
 * @date 2017/9/11 22:59
 */
public enum PlatformDict implements IMsgDict {
    /*******************************************************************************
     * {平台服务} ,编码code从0001开始,命名规则：{模块}_{包层级}_{提示级别}_{业务含义}
     *******************************************************************************/

    SYS_SERVER_ERROR_UNKNOW("00001", "未知异常，请联系管理员"),
    SYS_SERVER_ERROR_TooMany("00002", "查询一条记录，但返回多条"),
    SYS_SERVER_ERROR_BODY("00003", "缺少请求主体或反序列化失败"),
    SYS_SERVER_ERROR_NULL("00004", "空指针异常"),
    SYS_SERVER_ERROR_SQL("00005", "SQL语句有误"),
    SYS_SERVER_ERROR_DBEXIST("00008", "数据库中已存在该记录"),
    SYS_SERVER_ERROR_UNAUTH("000010", "没有权限，请联系管理员授权");


    /*******************************************************************************
     * 约定 api_1,编码code从1000开始
     *******************************************************************************/


    private String code;
    private String msg;

    private PlatformDict(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
