package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleVendorEntity;

/**
 * 角色与供应商关系（数据权限）表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-08 16:57:37
 */
@Mapper
public interface SysRoleVendorDao extends IBaseDao<SysRoleVendorEntity> {


	List<SysRoleVendorEntity> getNotSelectedCompany(Map<String, Object> map);

	List<SysRoleVendorEntity> getNotAdminSelectedCompany(Map<String, Object> map);

	void clear(Map<String, Object> map);

	void fastInsert(List<SysRoleVendorEntity> rrList);

	List<SysRoleVendorEntity> queryVendorsByRoleIds(List<Long> roleIdList);

	List<SysRoleVendorEntity> getSelectedCompany(Long roleId);
	
}
