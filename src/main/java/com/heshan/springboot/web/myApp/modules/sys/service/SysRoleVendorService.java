package com.heshan.springboot.web.myApp.modules.sys.service;


import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleVendorEntity;
import com.heshan.springboot.web.platform.common.utils.PageResult;

/**
 * 角色与供应商关系（数据权限）表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-08 16:57:37
 */
public interface SysRoleVendorService {
	
	SysRoleVendorEntity queryObject(Long id);
	
	List<SysRoleVendorEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleVendorEntity sysRoleVendor);
	
	void update(SysRoleVendorEntity sysRoleVendor);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<SysRoleVendorEntity> getSelectedCompany(Long roleId, SysUserEntity user);
	
	List<SysRoleVendorEntity> getNotSelectedCompany(Long roleId, SysUserEntity user);

	PageResult saveCompanyPerm(Long roleId, List<SysRoleVendorEntity> list, SysUserEntity sysUser);

	PageResult getAssignedCompanyMaterials(Long r, Integer c, Integer m);

	/**
	 * 
	 * @Title: queryVendorsByRoleIds 
	 * @Description: 根据角色id的列表获取对应的供应商列表（按照供应商编码去重）
	 * @param roleIdList
	 * @return List<SysRoleVendorEntity>    返回类型 
	 * @throws
	 */
	List<SysRoleVendorEntity> queryVendorsByRoleIds(List<Long> roleIdList);

	PageResult updateIsEnable(Long id, Integer isenable);


}
