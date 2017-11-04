package com.heshan.springboot.web.platform.common.constant;

/**
 * @Title: MessageEnum.java
 * @Package com.heshan.springboot.web.platform.common.constant
 * @Description: 消息状态码及常量
 * @author Frank
 * @Company www.flyheze.top
 * @date 2015年12月19日 下午11:08:38
 * @version V2.0
 */
public enum MessageEnum {

    TEST(1, "test"), SUCCESS(200, "交易完成"), LOGIN_FAIL(101, "登录失败"), LOGOUT_SUCCESS(104, "登出成功"), USER_NOT_FOUND(102,
            "未注册用户"), USER_DISABLED(103,
                    "无效用户"), NO_LOGIN(105, "未登录"), VERIFY_CODE_ERROR(106, "验证码错误"), SYSTEM_ERROR(100, "系统错误");

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    MessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
