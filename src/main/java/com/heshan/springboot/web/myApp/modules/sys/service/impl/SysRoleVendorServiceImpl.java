package com.heshan.springboot.web.myApp.modules.sys.service.impl;

import com.heshan.springboot.web.myApp.modules.sys.dao.SysRoleVendorDao;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserRoleDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysRoleVendorService;
import com.heshan.springboot.web.platform.common.utils.Constant;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heshan.springboot.web.myApp.modules.sys.dao.SysRoleDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleVendorEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sysRoleVendorService")
public class SysRoleVendorServiceImpl implements SysRoleVendorService {
	@Autowired
	private SysRoleVendorDao sysRoleVendorDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public SysRoleVendorEntity queryObject(Long id){
		return sysRoleVendorDao.queryObject(id);
	}
	
	@Override
	public List<SysRoleVendorEntity> queryList(Map<String, Object> map){
		return sysRoleVendorDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysRoleVendorDao.queryTotal(map);
	}
	
	@Override
	public void save(SysRoleVendorEntity sysRoleVendor){
		sysRoleVendorDao.save(sysRoleVendor);
	}
	
	@Override
	public void update(SysRoleVendorEntity sysRoleVendor){
		sysRoleVendorDao.update(sysRoleVendor);
	}
	
	@Override
	public void delete(Long id){
		sysRoleVendorDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysRoleVendorDao.deleteBatch(ids);
	}

	@Override
	public List<SysRoleVendorEntity> getSelectedCompany(Long roleId, SysUserEntity user){
			//根据角色ID , 在供应商目录和角色供应商对应表 , 查询供应商名称和供应商编码
			return sysRoleVendorDao.getSelectedCompany(roleId);
			
	}

	@Override
	public List<SysRoleVendorEntity> getNotSelectedCompany(Long roleId, SysUserEntity user){
			
			Map<String, Object> map = new HashMap<>();
			map.put("roleId",roleId);
			map.put("orgId", user.getOrgId());
			//超级用户
			if(user.getIsadmin() == Constant.SUPER_ADMIN){
				return sysRoleVendorDao.getNotSelectedCompany(map);
			}else{		
				return sysRoleVendorDao.getNotAdminSelectedCompany(map);
			}
		

	}

	@Override
	public PageResult saveCompanyPerm(Long roleId, List<SysRoleVendorEntity> list, SysUserEntity sysUser) {
		if (list == null) {
			return PageResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "参数异常");
		}

		try {
			if (roleId != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("roleId", roleId);
				map.put("orgId", sysUser.getOrgId());
				sysRoleVendorDao.clear(map);
			}

			List<SysRoleVendorEntity> rrList = new ArrayList<SysRoleVendorEntity>();
			for (SysRoleVendorEntity roleCompany : list) {
				roleCompany.setRoleId(roleId);
				roleCompany.setCreateTime(System.currentTimeMillis());
				roleCompany.setCreateUser(sysUser.getUserId());
				// 添加医院信息
				roleCompany.setOrgId(sysUser.getOrgId());
				rrList.add(roleCompany);
			}
			sysRoleVendorDao.fastInsert(rrList);
		} catch (Exception e) {
			return PageResult.error("保存功能菜单权限异常");
		}

		return PageResult.ok();
	}

	@Override
	public PageResult getAssignedCompanyMaterials(Long roleId, Integer c, Integer m) {
		if (roleId == null || (c==null && m==null)) {
			return PageResult.error("参数异常");
		}
		if(c != null && (c !=0 && c!=1)){
			return PageResult.error("参数异常");
		}
		if(m != null && (m !=0 && m!=1)){
			return PageResult.error("参数异常");
		}
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("roleId", roleId);
		hashMap.put("allVendors", c);
		hashMap.put("allMaterials", m);
		
		try {
			int r = 0;
			if(c!=null){
				r = sysRoleDao.update(hashMap);
			}else{
				r = sysRoleDao.update(hashMap);
			}
			if(r>0){
				return PageResult.ok();
			}
		} catch (Exception e) {
			return PageResult.error("获取该角色下已分配供应商数量异常");
		}
		return PageResult.ok();

	}

	@Override
	public List<SysRoleVendorEntity> queryVendorsByRoleIds(List<Long> roleIdList) {
		return sysRoleVendorDao.queryVendorsByRoleIds(roleIdList);
	}


	@Override
	public PageResult updateIsEnable(Long roleId, Integer isenable) {
		if (roleId == null || isenable == null) {
			return PageResult.error("参数异常");
		}

		try {
			if (isenable == 0) {
				List<Long> count = sysUserRoleDao.queryUserRoleIdList(roleId);
				if (!count.isEmpty()) {
					return PageResult.error(HttpStatus.SC_INSUFFICIENT_STORAGE, "该角色已授予用户权限，不能被禁用");
				}
			}
			Map<String, Object> hashMap = new HashMap<>();
			hashMap.put("roleId", roleId);
			hashMap.put("isenable", isenable);
			sysRoleDao.update(hashMap);
			return PageResult.ok();
		} catch (Exception e) {
			return PageResult.error("角色禁用启用异常");
		}

	}
	
}
