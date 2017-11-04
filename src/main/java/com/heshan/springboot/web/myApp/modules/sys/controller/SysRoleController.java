package com.heshan.springboot.web.myApp.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysRoleService;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.platform.common.annotation.SysLog;
import com.heshan.springboot.web.platform.common.validator.ValidatorUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleVendorEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysRoleVendorService;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.common.utils.PageUtils;
import com.heshan.springboot.web.platform.common.utils.QueryObject;
import com.heshan.springboot.web.platform.support.AbstractCtrl;

import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 * 
 * @author
 * @email
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractCtrl {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleVendorService sysRoleVendorService;
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	public PageResult list(@RequestParam Map<String, Object> params){
		//查询列表数据
		QueryObject query = new QueryObject(params);
		List<SysRoleEntity> list = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return PageResult.ok().put("page", pageUtil);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	public PageResult select(){
		Map<String, Object> map = new HashMap<>();
		List<SysRoleEntity> list = sysRoleService.queryList(map);
		
		return PageResult.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping(value = "/info/{roleId}", method = RequestMethod.GET)
	public PageResult info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.queryObject(roleId);
		return PageResult.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public PageResult save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		role.setOrgId(getOrgId());
		return sysRoleService.save(role);
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	public PageResult update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return PageResult.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping(value = "/delete")
	public PageResult delete(@RequestBody Long[] roleIds){
		return sysRoleService.deleteBatch(roleIds);
		 
	}
	
	 /**
     * 一次加载可选与不可选供应商
     */
	@SysLog("一次加载可选与不可选供应商")
    @RequestMapping(value = "/getRoleCompany/{roleId}", method = RequestMethod.GET)
    public PageResult getRoleCompany(@PathVariable("roleId") Long roleId) {
    	if(roleId == null) {
    		return PageResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "参数异常");
    	}
    	//查询角色拥有的供应商
    	List<SysRoleVendorEntity> assignedCompany = sysRoleVendorService.getSelectedCompany(roleId,getUser());
    	//查询角色未拥有的供应商
    	List<SysRoleVendorEntity> availableCompany = sysRoleVendorService.getNotSelectedCompany(roleId,getUser());

    	Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignedCompany", assignedCompany);
        roleMap.put("availableCompany", availableCompany);
        
        return PageResult.ok(roleMap);
    }
	
	 /**
	  * 
	  * 
     * 
    * 保存供应商权限
     */
	@SysLog("保存供应商权限")
    @RequestMapping(value = "/saveCompanyPerm", method = RequestMethod.POST)
	@ApiOperation(value = "保存供应商权限")
    public PageResult saveCompanyPerm(@RequestBody List<SysRoleVendorEntity> list) {
		SysUserEntity sysUser = getUser();
		Long roleId= null;
		for(SysRoleVendorEntity roList:list) {
			roleId = roList.getRoleId();
			break;
		}
		PageResult result = sysRoleVendorService.saveCompanyPerm(roleId, list,sysUser);
        return result;
    }
	
	/** 
	 * 角色是否拥有全部供应商权限 , 0否 1是
	 * */
    @RequestMapping(value ="/getAssignedCompanyMaterials",method = RequestMethod.POST)
    @ApiOperation(value = "角色是否拥有全部供应商权限 , 0否 1是")
    public PageResult getAssignedCompanyMaterials(@RequestBody SysRoleEntity role) {
    	Long r = role.getRoleId();
    	Integer c = role.getAllVendors();
    	Integer m = role.getAllMaterials();
    	return  sysRoleVendorService.getAssignedCompanyMaterials(r,c,m);
    }
    
    /**
     * 
    *修改角色状态
     */
    @RequestMapping(value = "/updateIsEnable", method = RequestMethod.POST)
    public PageResult updateIsEnable(@RequestBody SysRoleEntity role) {
    	return sysRoleVendorService.updateIsEnable(role.getRoleId(), role.getIsenable());
    }
    
}
