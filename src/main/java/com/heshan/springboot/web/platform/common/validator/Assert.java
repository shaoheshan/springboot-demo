package com.heshan.springboot.web.platform.common.validator;

import com.heshan.springboot.web.platform.common.utils.CommUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import com.heshan.springboot.web.platform.common.exception.BizException;

import java.util.Collection;

/**
 * 断言工具类
 *
 * @author Frank
 * @email
 * @date 2017-03-23 15:50
 */
@Slf4j
public abstract class Assert {


    /**
     * 判断对象是否为空，不满足条件则抛出异常
     *
     * @param obj 对象
     * @param msg 异常消息
     * @return
     */
    public static void hasBlank(Object obj, String msg, Object... vals) {
        if (obj == null) {
            toException(msg, vals);
        }
        if (obj instanceof Collection && ((Collection) obj).size() == 0) {
            toException(msg, vals);
        }
        if (obj instanceof String && StringUtils.isBlank(obj.toString())) {
            toException(msg, vals);
        }
    }


    private static void toException(String msg, Object... vals) {
        msg = CommUtils.strMerge(msg, vals);
        log.error(msg);
        throw new BizException(msg);
    }
}
