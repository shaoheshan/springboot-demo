package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.List;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import com.heshan.springboot.web.platform.support.IBaseDao;

/**
 * 用户与角色对应关系
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:34:46
 */
@Mapper
public interface SysUserRoleDao extends IBaseDao<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	List<Long> queryUserRoleIdList(Long roleId);
}
