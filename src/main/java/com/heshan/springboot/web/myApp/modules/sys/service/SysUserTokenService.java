package com.heshan.springboot.web.myApp.modules.sys.service;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserTokenEntity;
import com.heshan.springboot.web.platform.common.utils.PageResult;

/**
 * 用户Token
 * 
 * @author
 * @email
 * @date 2017-07-23 15:22:07
 */
public interface SysUserTokenService {

	SysUserTokenEntity queryByUserId(Long userId);

	SysUserTokenEntity queryByToken(String token);

	void save(SysUserTokenEntity token);

	void update(SysUserTokenEntity token);

	/**
	 * 生成token,并保存到数据库，如果开户使用Redis，也可保存到Redis专业缓存系统中
	 * 
	 * @param userId
	 *            用户ID
	 */
	PageResult createToken(long userId);

}
