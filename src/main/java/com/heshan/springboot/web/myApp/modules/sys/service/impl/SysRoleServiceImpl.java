package com.heshan.springboot.web.myApp.modules.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heshan.springboot.web.myApp.modules.sys.dao.SysRoleDao;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserRoleDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysRoleService;
import com.heshan.springboot.web.platform.common.utils.PageResult;

/**
 * 角色
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:45:12
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	@Override
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public PageResult save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		Long i = sysRoleDao.isRoleName(role);
		if(i > 0) {
			return PageResult.error("角色名称已存在");
		}
		sysRoleDao.save(role);
		return PageResult.ok();
	}

	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleDao.update(role);
	}

	@Override
	@Transactional
	public PageResult deleteBatch(Long[] roleIds) {
		for (Long roleId : roleIds) {
			List<Long> count = sysUserRoleDao.queryUserRoleIdList(roleId);
			if (!count.isEmpty()) {
				return PageResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "该角色已授予用户权限，不能被删除");
			}else{
				sysRoleDao.deleteBatch(roleIds);
				return PageResult.ok("删除成功");
			}
		}
		return PageResult.ok();
	}

	@Override
	public List<SysRoleEntity> orleList() {
		return sysRoleDao.orleList();
	}
	
	// 保存角色与菜单关系
	//		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
	// 更新角色与菜单关系
	    //sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

}
