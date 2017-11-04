package com.heshan.springboot.web.myApp.modules.sys.service;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author
 * @email
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     */
    SysUserEntity queryUser(Long userId);

    /**
     * 更新token失效时间
     *
     * @param token
     * @param userId
     * @return
     */
    int updateTokenExpireTime(String token, Long userId);
}
