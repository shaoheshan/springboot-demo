package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.HashMap;
import java.util.List;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysResourceEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单资源表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-23 14:24:54
 */
@Mapper
public interface SysResourceDao extends IBaseDao<SysResourceEntity> {

	List<SysResourceEntity> getResourceByRole(HashMap<String, Object> map);

	void deleteRo(HashMap<String, Object> hashMap);

	List<SysResourceEntity> roReList(HashMap<String, Object> hashMap);

	List<SysResourceEntity> queryResourceList(HashMap<String,Object> map);

	List<SysResourceEntity> getUserResourceList(HashMap<String, Object> map);
	
}
