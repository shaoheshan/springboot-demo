package com.heshan.springboot.web.platform.common.exception;

import com.heshan.springboot.web.platform.common.utils.CommUtils;
import com.heshan.springboot.web.platform.common.web.IMsgDict;

/**
 * 自定义异常
 * <p>
 * TODO 异常处理还需要进一步规范化、细化
 *
 * @author
 * @email
 * @date 2016年10月27日 下午10:11:27
 */
public class BizException extends AbsException {
    private String msg;
    private String code = "500";

    public BizException(IMsgDict obj) {
        this.msg = obj.getMsg();
        this.code = obj.getCode();
    }

    public BizException(IMsgDict obj, Throwable e) {
        e.printStackTrace();
        this.msg = obj.getMsg();
        this.code = obj.getCode();
    }

    public BizException(String msg, Object... argArray) {
        this.msg = CommUtils.strMerge(msg, argArray);
    }


    public BizException(String code, String msg, Object... argArray) {
        this.msg = CommUtils.strMerge(msg, argArray);
        this.code = code;
    }

    public BizException(String msg, Throwable e, Object... argArray) {
        e.printStackTrace();
        this.msg = CommUtils.strMerge(msg, argArray);
    }

    public BizException(String code, String msg, Throwable e, Object... argArray) {
        e.printStackTrace();
        this.code = code;
        this.msg = CommUtils.strMerge(msg, argArray);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
