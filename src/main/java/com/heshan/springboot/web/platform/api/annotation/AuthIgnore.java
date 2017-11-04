package com.heshan.springboot.web.platform.api.annotation;

import java.lang.annotation.*;

/**
 * api接口，忽略access_token验证
 * @author
 * @email
 * @date 2017-03-23 15:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

}
