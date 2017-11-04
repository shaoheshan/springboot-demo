package com.heshan.springboot.web.platform.support;

import org.apache.shiro.SecurityUtils;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import org.apache.shiro.subject.Subject;

/**
 * Controller公共组件
 *
 * @author
 * @email
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractCtrl {

    protected SysUserEntity getUser() {
        Subject subject = SecurityUtils.getSubject();
        return (SysUserEntity) subject.getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

    protected String getOrgId() {
        return getUser().getOrgId();
    }

    protected Integer getIsadmin() {
        return getUser().getIsadmin();
    }


    //TODO 数据权限
}
