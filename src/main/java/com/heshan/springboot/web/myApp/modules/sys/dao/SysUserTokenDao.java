package com.heshan.springboot.web.myApp.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserTokenEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;

/**
 * 系统用户Token
 * 
 * @author
 * @email
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface SysUserTokenDao extends IBaseDao<SysUserTokenEntity> {
    
    SysUserTokenEntity queryByUserId(Long userId);

    SysUserTokenEntity queryByToken(String token);
	
}
