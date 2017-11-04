package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.List;

import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:33:46
 */
@Mapper
public interface SysRoleMenuDao extends IBaseDao<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
