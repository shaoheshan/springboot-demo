package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.List;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleResourceEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色资源关系表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-24 09:03:11
 */
@Mapper
public interface SysRoleResourceDao extends IBaseDao<SysRoleResourceEntity> {

	void insertRoleResource(List<SysRoleResourceEntity> list);
	
}
