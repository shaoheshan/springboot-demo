/**   
* @Title: package-info.java 
* @Package com.heshan.springboot.web.platform.modules.token
* @Description: 
* @author Frank
* @date 2017年8月23日 下午4:37:07 
* @version V2.0   
*/
/** 
* @ClassName: package-info 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author
* @date 2017年8月23日 下午4:37:07 
*  
*/
package com.heshan.springboot.web.platform.modules.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: AutoToken
 * @Description: 方法注解
 * @author frank
 * @date 2017年5月19日 下午1:15:54
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoToken {
    /**
     * Token动作:创建OR验证
     * 
     * @return
     */
    TokenHandler[] scope() default {};
}