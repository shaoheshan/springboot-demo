package com.heshan.springboot.web.platform.common.utils;

import com.heshan.springboot.web.platform.common.exception.BizException;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 * @author
 * @email
 * @date 2011年11月12日 上午9:49:19
 */
@Slf4j
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登陆人对象
     */
    public static SysUserEntity getUserEntity() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登陆人的用户id
     */
    public static Long getUserId() {
        return getUserEntity().getUserId();
    }

    /**
     * 获取当前登陆人的用户id
     *
     * @param local true本地调用 false前置服务调用
     * @return
     */
    public static Long getUserId(Boolean local) {
        Long userId = null;
        try {
            userId = ShiroUtils.getUserId();
        } catch (Exception e) {
            if (!local) {
                log.warn("无法获取到当前登陆用户，调用场景可能为API接口调用。");
            }
        }
        return userId;
    }

    /**
     * 获取当前登陆人的机构ID
     */
    public static String getOrgId() {
        return getUserEntity().getOrgId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new BizException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

}
