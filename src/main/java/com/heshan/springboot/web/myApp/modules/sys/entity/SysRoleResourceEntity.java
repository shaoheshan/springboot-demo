package com.heshan.springboot.web.myApp.modules.sys.entity;

import java.io.Serializable;


/**
 * 角色资源关系表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-24 09:03:11
 */
//@Data
public class SysRoleResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long roleId;
	//资源ID
	private Long resId;
	//
	private String orgId;
	//
	private Long createTime;
	//
	private Long createUser;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	/**
	 * 获取：
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * 设置：资源ID
	 */
	public void setResId(Long resId) {
		this.resId = resId;
	}
	/**
	 * 获取：资源ID
	 */
	public Long getResId() {
		return resId;
	}
	/**
	 * 设置：
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * 获取：
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Long getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：
	 */
	public Long getCreateUser() {
		return createUser;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resId == null) ? 0 : resId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysRoleResourceEntity other = (SysRoleResourceEntity) obj;
		if (resId == null) {
			if (other.resId != null)
				return false;
		} else if (!resId.equals(other.resId))
			return false;
		return true;
	}
}
