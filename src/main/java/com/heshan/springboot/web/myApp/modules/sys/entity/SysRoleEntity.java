package com.heshan.springboot.web.myApp.modules.sys.entity;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:27:38
 */
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;

	private List<Long> menuIdList;

	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	//1,启用 0.禁用
	private Integer isenable;
	//org_id
	private String orgId;
	//医院名称
	private String hospitalName;
	//默认为1 1:有效数据 0:逻辑删除数据
	private Integer status;
	//创建人
	private Long createUser;
	//修改时间
	private Long updateTime;
	//修改人
	private Long updateUser;
	//是否有所有供应商数据权限，0否，1是
	private Integer allVendors;
	//是否有所有材料数据权限，0否，1是
	private Integer allMaterials;

	/**
	 * 设置：
	 * @param roleId 
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置：角色名称
	 * @param roleName 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 获取：角色名称
	 * @return String
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * 设置：备注
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
	}
	
	/**
	 * 设置：1,启用 0.禁用
	 */
	public void setIsenable(Integer isenable) {
		this.isenable = isenable;
	}
	/**
	 * 获取：1,启用 0.禁用
	 */
	public Integer getIsenable() {
		return isenable;
	}
	/**
	 * 设置：org_id
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * 获取：org_id
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * 设置：医院名称
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	/**
	 * 获取：医院名称
	 */
	public String getHospitalName() {
		return hospitalName;
	}
	/**
	 * 设置：默认为1 1:有效数据 0:逻辑删除数据
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：默认为1 1:有效数据 0:逻辑删除数据
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建人
	 */
	public Long getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Long getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：修改人
	 */
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取：修改人
	 */
	public Long getUpdateUser() {
		return updateUser;
	}
	/**
	 * 设置：是否有所有供应商数据权限，0否，1是
	 */
	public void setAllVendors(Integer allVendors) {
		this.allVendors = allVendors;
	}
	/**
	 * 获取：是否有所有供应商数据权限，0否，1是
	 */
	public Integer getAllVendors() {
		return allVendors;
	}
	/**
	 * 设置：是否有所有材料数据权限，0否，1是
	 */
	public void setAllMaterials(Integer allMaterials) {
		this.allMaterials = allMaterials;
	}
	/**
	 * 获取：是否有所有材料数据权限，0否，1是
	 */
	public Integer getAllMaterials() {
		return allMaterials;
	}
}
