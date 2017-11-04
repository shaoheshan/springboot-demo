package com.heshan.springboot.web.platform.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.heshan.springboot.web.platform.common.enums.DataPermission;

/**
 * 
 * @ClassName: RequiresDataPermission 
 * @Description: 设置需要校验数据权限 
 * @author Carry
 * @date 2017年8月14日 下午1:29:46 
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresDataPermission {
    
	String value() default "";
	
	/**
	 * 
	 * @Title: data 
	 * @Description: 需要校验的数据权限集合
	 * @return DataPermission[]    返回类型 
	 * @throws
	 */
	DataPermission[] data() default {};
}
