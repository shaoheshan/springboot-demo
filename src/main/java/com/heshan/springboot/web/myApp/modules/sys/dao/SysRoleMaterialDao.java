package com.heshan.springboot.web.myApp.modules.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleMaterialEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;

/**
 * 角色与材料关系（数据权限）表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-08 16:57:37
 */
@Mapper
public interface SysRoleMaterialDao extends IBaseDao<SysRoleMaterialEntity> {

	List<String> getMaterialsCodes(Long roleId);

	List<SysRoleMaterialEntity> getIsMaterials(Map<String, Object> map);

	List<SysRoleMaterialEntity> getIsNOMaterials(Map<String, Object> map);

	List<SysRoleMaterialEntity> queryMaterialsByRoleIds(List<Long> roleIdList);

	List<SysRoleMaterialEntity> getSelectedMaterialsCatalog(HashMap<String, Object> map);

	void clear(HashMap<String, Object> hashMap);

	void fastInsert(List<SysRoleMaterialEntity> list);

	List<SysRoleMaterialEntity> getmaterialType(Map<String, Object> map);

	
}
