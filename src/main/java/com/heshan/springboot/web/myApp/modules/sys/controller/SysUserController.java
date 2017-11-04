package com.heshan.springboot.web.myApp.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysRoleService;
import com.heshan.springboot.web.myApp.modules.sys.service.SysUserService;
import com.heshan.springboot.web.platform.common.annotation.SysLog;
import com.heshan.springboot.web.platform.common.validator.Assert;
import com.heshan.springboot.web.platform.common.validator.ValidatorUtils;
import com.heshan.springboot.web.platform.common.validator.group.AddGroup;
import com.heshan.springboot.web.platform.support.AbstractCtrl;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.platform.common.utils.Constant;
import com.heshan.springboot.web.platform.common.utils.DateUtils;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.common.utils.PageUtils;
import com.heshan.springboot.web.platform.common.utils.QueryObject;

/**
 * 系统用户
 * 
 * @author
 * @email
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractCtrl {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	public PageResult list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		SysUserEntity user = getUser();
		if(user.getIsadmin() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		
		//查询列表数据
		QueryObject query = new QueryObject(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return PageResult.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public PageResult info(){
		return PageResult.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/updatePassword")
	public PageResult password(@RequestBody Map<String, Object> params){
		if(params.get("newPassword")!=null&&params.get("password")!=null) {
			//新密码
			String newPassword =params.get("newPassword").toString();
			//原密码
			String password =params.get("password").toString();

			Assert.hasBlank(newPassword, "新密码不为能空");

			//sha256加密
			password = new Sha256Hash(password, getUser().getSalt()).toHex();
			//sha256加密
			newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();
					
			//更新密码
			int count = sysUserService.updatePassword(getUserId(), password, newPassword);
			if(count == 0){
				return PageResult.error("原密码不正确");
			}
			
			return PageResult.ok();
		}
		return PageResult.error("参数异常");
		
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	public PageResult info(@PathVariable("userId") Long userId){
		if(userId!=null) {
			SysUserEntity user = sysUserService.queryObject(userId);
			return PageResult.ok().put("user", user);
		}
		return PageResult.error();
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	public PageResult save(@RequestBody SysUserEntity user){
			ValidatorUtils.validateEntity(user, AddGroup.class);
			user.setOrgId(getOrgId());
			user.setCreateUserId(getUserId());
			return sysUserService.save(user);
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	public PageResult update(@RequestBody SysUserEntity user){
		if(user!=null) {
			user.setUpdateUser(getUserId());
			user.setUpdateTime(DateUtils.getUnixTimeMis());
			sysUserService.updateUser(user);
			return PageResult.ok();
		}
		return PageResult.error();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	public PageResult delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return PageResult.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return PageResult.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return PageResult.ok();
	}
	
	/**
	 * 重置密码
	 */
	@SysLog("重置密码")
	@RequestMapping("/resetPwd/{userId}")
	public PageResult resetPwd(@PathVariable("userId") Long userId){
		if(userId!=null) {
			//传入的用户信息
			SysUserEntity user = sysUserService.queryObject(userId);
			//是否为超级管理员 , 是才可以进行重置密码操作
			if(getUser().getIsadmin() == 1){
				//随机生成6位数字作为密码
				int resetPwd =(int) ((Math.random()*9+1)*100000);
				//sha256加密
				String newPassword = new Sha256Hash(String.valueOf(resetPwd), user.getSalt()).toHex();
				// 要重置密码的用户
		        SysUserEntity userEntity = sysUserService.queryObject(userId);
		        userEntity.setPassword(newPassword);
		        userEntity.setUpdateTime(DateUtils.getUnixTimeMis());
		        userEntity.setUpdateUser(getUserId());
		        sysUserService.updatePass(userEntity);
		        return PageResult.ok(resetPwd+"");
			}
			return PageResult.ok();
		}
		return PageResult.error("参数异常");
	}
	
	/**
	 * 禁用启用用户
	 */
	@SysLog("禁用启用用户")
	@RequestMapping("/isNoUser")
	public PageResult isNoUser(@RequestBody SysUserEntity user){
		//用户无法禁用自己这个账号
		if(user.getUserId() == getUserId()) {
			user.setIsenable(1);
		}
		user.setUpdateUser(getUserId());
		user.setUpdateTime(DateUtils.getUnixTimeMis());
		sysUserService.isNoUser(user);
		
		return PageResult.ok();
	}
	
	/*
	 * 
	 * 用户分配角色保存
	 * */
	@RequestMapping("/saveOrUser")
	public PageResult saveOrUser(@RequestBody SysUserEntity user){
		if(user.getUserId()!=null && user.getRoleIdList()!=null) {
			// 保存用户与角色关系
			sysUserService.saveOrUser(user.getUserId(), user.getRoleIdList());
			return PageResult.ok();
		} 
		return PageResult.error("参数不正确");
	}
	
	/*
	 * 
	 * 用户角色查询
	 * */
	@RequestMapping("/OrUserList")
	public PageResult OrUserList(@RequestBody SysUserEntity user){
		if(user.getUserId()!=null) {
			//当前用户所拥有的角色
			List<SysRoleEntity> orUserList = sysUserService.OrUserList(user.getUserId());
			//查询所有角色
			List<SysRoleEntity> orleList = sysRoleService.orleList();
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("orUserList", orUserList);
			hashMap.put("orleList", orleList);
			//返回角色List集合
			return PageResult.ok().put("hashMap",hashMap);
		}
		return PageResult.error("用户id为空");
	}
}
