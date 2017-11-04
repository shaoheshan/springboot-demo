package com.heshan.springboot.web.myApp.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysMenuEntity;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleResourceEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.TreeJson;
import com.heshan.springboot.web.myApp.modules.sys.service.SysUserService;
import com.heshan.springboot.web.platform.common.utils.TreeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heshan.springboot.web.myApp.modules.sys.dao.SysMenuDao;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysResourceDao;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysRoleResourceDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysResourceEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysMenuService;
import com.heshan.springboot.web.platform.common.utils.Constant.MenuType;
import com.heshan.springboot.web.platform.common.utils.DateUtils;
import com.heshan.springboot.web.platform.common.utils.PageResult;


@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysResourceDao sysResourceDao;
	@Autowired
	private SysRoleResourceDao sysRoleResourceDao;
	@Autowired
    private SysUserDao sysUserDao;
	
	
	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return sysMenuDao.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return sysMenuDao.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public SysMenuEntity queryObject(Long menuId) {
		return sysMenuDao.queryObject(menuId);
	}

	@Override
	public List<SysMenuEntity> queryList(Map<String, Object> map) {
		return sysMenuDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysMenuDao.queryTotal(map);
	}

	@Override
	public void save(SysMenuEntity menu) {
		sysMenuDao.save(menu);
	}

	@Override
	public void update(SysMenuEntity menu) {
		sysMenuDao.update(menu);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] menuIds) {
		sysMenuDao.deleteBatch(menuIds);
	}
	
	@Override
	public List<SysMenuEntity> queryUserList(Long userId) {
		return sysMenuDao.queryUserList(userId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
		
		for(SysMenuEntity entity : menuList){
			if(entity.getType() == MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}

	@Override
	public PageResult getResourceByRole(Long roleId,SysUserEntity user) {

		if (roleId == null) {
			return PageResult.error("参数异常");
		}
		try {
		List<TreeJson> tree=new ArrayList<TreeJson>();
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> hashMap = new HashMap<>();
		map.put("orgId", user.getOrgId());
		map.put("roleId", roleId);
		List<SysResourceEntity> list= sysResourceDao.getResourceByRole(map);
		Iterator<SysResourceEntity> iterator = list.iterator();
		if(CollectionUtils.isNotEmpty(list)) {
			while(iterator.hasNext()){
			SysResourceEntity b = iterator.next();
			if(b.getName().equals("用户管理") || b.getName().equals("角色管理")) {
				 iterator.remove();
			}
			}
			for(SysResourceEntity menu : list) {
				tree.add(menu.toTree());
			}
		}
		tree = TreeUtil.getTree(tree, null);
		hashMap.put("tree", tree);
		return PageResult.ok(hashMap);
	} catch (Exception e) {
		e.printStackTrace();
		return PageResult.error("获取该角色下数据异常");
	}
}



	

	@Override
	public PageResult addFuncMenuPerm(Long roleId, List<SysRoleResourceEntity> list, SysUserEntity sysUserEntity) {
		if (list == null) {
			return PageResult.error("参数异常");
		}
		try {
			if (roleId != null) {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("orgId", sysUserEntity.getOrgId());
				hashMap.put("roleId", roleId);
				List<SysResourceEntity> queryList = sysResourceDao.roReList(hashMap);
				if(!queryList.isEmpty()) {
					sysResourceDao.deleteRo(hashMap);
				}
				
			}

			List<SysRoleResourceEntity> rrList = new ArrayList<SysRoleResourceEntity>();
			for (SysRoleResourceEntity roleCompany : list) {
				roleCompany.setRoleId(roleId);
				roleCompany.setCreateTime(DateUtils.getUnixTimeMis());
				roleCompany.setCreateUser(sysUserEntity.getUserId());
				// 添加医院信息
				roleCompany.setOrgId(sysUserEntity.getOrgId());
				rrList.add(roleCompany);
			}
			sysRoleResourceDao.insertRoleResource(rrList);
			return PageResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return PageResult.error("保存功能菜单权限异常");
		}
	}

	/*@Override
	public PageResult queryResourceList(Long userId) {
		List<TreeJson> tree=new ArrayList<TreeJson>();
		HashMap<String, Object> hashMap = new HashMap<>();
		HashMap<String, Object> map = new HashMap<>();
		//根据用户ID查询orgId
		SysUserEntity queryObject = sysUserDao.queryObject(userId);
		//根据orgId查询医院信息
		DictHospitalEntity queryOrgIdList = dictHospitalDao.queryOrgIdList(queryObject.getOrgId());
		  //三端模式	
		  if(queryOrgIdList.getModelType() == 2) {
			  map.put("hasModel", 0);
		  }
		List<SysResourceEntity> list= sysResourceDao.queryResourceList(map);
		if(CollectionUtils.isNotEmpty(list)) {
			for(SysResourceEntity menu : list) {
				tree.add(menu.toTree());
			}
		}
		tree = TreeUtil.getTree(tree, null);
		hashMap.put("tree", tree);
		return PageResult.ok(hashMap);
	} */


	/*@Override
	public PageResult getUserResourceList(Long userId) {
		if (userId == null) {
			return PageResult.error("参数异常");
		}
		try {
			List<TreeJson> tree = new ArrayList<>();
			HashMap<String, Object> map = new HashMap<>();
			HashMap<String, Object> hashMap = new HashMap<>();
			map.put("userId", userId);
			//当前用户菜单权限
			List<SysResourceEntity> userResourceList = sysResourceDao.getUserResourceList(map);
			//根据用户ID查询orgId
			SysUserEntity queryObject = sysUserDao.queryObject(userId);
			//根据orgId查询医院信息
			DictHospitalEntity queryOrgIdList = dictHospitalDao.queryOrgIdList(queryObject.getOrgId());
			  //三端模式	
			  if(queryOrgIdList.getModelType() == 2) {
				  map.put("hasModel", 0);
			  }
			//所有菜单权限
			List<SysResourceEntity> all = sysResourceDao.queryResourceList(map);
			List<Long> checkedIds = new ArrayList<>();
			userResourceList.forEach(ur -> checkedIds.add(ur.getId()));
			List<Long> checkedIdsContainParents = getCheckedIdsContainParents(all, checkedIds);
			for(SysResourceEntity menu : all) {
				if (checkedIdsContainParents.contains(menu.getId())) {
					tree.add(menu.toTree());
				}
			}
			tree = TreeUtil.getTree(tree, null);
			hashMap.put("tree", tree);
			return PageResult.ok(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			return PageResult.error("获取该角色下数据异常");
		}
	}
*/
	private List<Long> getCheckedIdsContainParents(List<SysResourceEntity> all, List<Long> checkedIds) {
		boolean added = false;
		for (SysResourceEntity sr : all) {
			Long parentId = sr.getParentId();
			Long id = sr.getId();
			if (parentId > 0 && checkedIds.contains(id) && !checkedIds.contains(parentId)) {
				checkedIds.add(parentId);
				added = true;
			}
		}
		if (!added) {
			return checkedIds;
		}
		return getCheckedIdsContainParents(all, checkedIds);
	}
}
