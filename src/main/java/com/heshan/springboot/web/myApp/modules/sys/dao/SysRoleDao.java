package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.List;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
public interface SysRoleDao extends IBaseDao<SysRoleEntity> {

	List<SysRoleEntity> queryRoleList(List<Long> roleId);

	List<SysRoleEntity> orleList();

	Long isRoleName(SysRoleEntity role);

}
