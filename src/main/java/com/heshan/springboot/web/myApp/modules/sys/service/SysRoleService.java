package com.heshan.springboot.web.myApp.modules.sys.service;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.platform.common.utils.PageResult;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	PageResult save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	PageResult deleteBatch(Long[] roleIds);

	List<SysRoleEntity> orleList();

}
