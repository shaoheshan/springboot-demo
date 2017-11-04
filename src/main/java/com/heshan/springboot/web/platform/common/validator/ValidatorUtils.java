package com.heshan.springboot.web.platform.common.validator;

import com.alibaba.fastjson.JSONObject;
import com.heshan.springboot.web.platform.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * hibernate-validator校验工具类 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author
 * @email
 * @date 2012-03-15 10:50
 */
@Slf4j
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     *
     * @param obj    待校验对象
     * @param groups 待校验的组
     * @throws BizException 校验不通过，则向上层抛出BizException异常
     */
    public static void validateEntity(Object obj, Class<?>... groups) throws BizException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj, groups);
        if (!constraintViolations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                errors.put(constraint.getPropertyPath().toString(), constraint.getMessage());
            }
            String str = "数据校验失败，校验对象：{}, 校验所属分组：{}，失败原因：{}";
            String errorStr = JSONObject.toJSONString(errors);
            log.warn(str, obj.getClass().getSimpleName(), groups[0].getSimpleName(), errorStr);
            throw new BizException(errorStr);
        }
    }
}
