package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
public interface SysUserDao extends IBaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);

	void isNoUser(SysUserEntity user);

	void deleteBatchRoUs(Long[] userId);
	//根据用户名查询
	Long isUserName(SysUserEntity sysUser);
}
