package com.heshan.springboot.web.myApp.modules.sys.service;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysMenuEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleResourceEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.platform.common.utils.PageResult;

import java.util.List;
import java.util.Map;


/**
 * 菜单管理
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:42:16
 */
public interface SysMenuService {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<SysMenuEntity> getUserMenuList(Long userId);
	
	/**
	 * 查询菜单
	 */
	SysMenuEntity queryObject(Long menuId);
	
	/**
	 * 查询菜单列表
	 */
	List<SysMenuEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存菜单
	 */
	void save(SysMenuEntity menu);
	
	/**
	 * 修改
	 */
	void update(SysMenuEntity menu);
	
	/**
	 * 删除
	 */
	void deleteBatch(Long[] menuIds);
	
	/**
	 * 查询用户的权限列表
	 */
	List<SysMenuEntity> queryUserList(Long userId);
	//查询角色所拥有的数据权限
	PageResult getResourceByRole(Long roleId, SysUserEntity user);
	//保存供应商权限
	PageResult addFuncMenuPerm(Long roleId, List<SysRoleResourceEntity> list, SysUserEntity sysUserEntity);
	//查询所有权限
	//PageResult queryResourceList(Long userId);
	//根据用户id查询权限
	//PageResult getUserResourceList(Long userId);
}
