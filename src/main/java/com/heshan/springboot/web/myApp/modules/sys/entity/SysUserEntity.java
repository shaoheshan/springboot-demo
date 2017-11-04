package com.heshan.springboot.web.myApp.modules.sys.entity;

import java.io.Serializable;
import java.util.List;

import com.heshan.springboot.web.platform.common.validator.group.AddGroup;
import com.heshan.springboot.web.platform.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 系统用户
 * 
 * @author
 * @email
 * @date 2016年9月18日 上午9:28:55
 */
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	transient private String password;

	/**
	 * 盐
	 */
	transient private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	
	/**
	 * 角色ID列表
	 */
	private List<Long> roleIdList;
	
	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Long createTime;
	
	//昵称
	private String name;
	//备注
	private String remark;
	//医院名称
	private String hospitalName;
	//医院唯一ID
	private String orgId;
	//是否为超级管理员 0:普通员工 1:超级管理员
	private Integer isadmin;
	//0,禁用 1.启用
	private Integer isenable;
	//修改时间
	private Long updateTime;
	//修改人
	private Long updateUser;
	
	// 当前用户拥有的数据权限（所有供应商、材料的数据权限）
    private boolean allVendors = false;
    private boolean allMaterials = false;

	/**
	 * 设置：
	 * @param userId 
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * 设置：用户名
	 * @param username 用户名
	 */
	public void sysUsers(String username) {
		this.username = username;
	}

	/**
	 * 获取：用户名
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
        this.username = username;
    }

    /**
	 * 设置：密码
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置：邮箱
	 * @param email 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 设置：手机号
	 * @param mobile 手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：手机号
	 * @return String
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置：状态  0：禁用   1：正常
	 * @param status 状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态  0：禁用   1：正常
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置：创建时间
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 * @return Date
	 */
	public Long getCreateTime() {
		return createTime;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 设置：昵称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：昵称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
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
	 * 设置：医院唯一ID
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * 获取：医院唯一ID
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * 设置：是否为超级管理员 0:普通员工 1:超级管理员
	 */
	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}
	/**
	 * 获取：是否为超级管理员 0:普通员工 1:超级管理员
	 */
	public Integer getIsadmin() {
		return isadmin;
	}
	/**
	 * 设置：0,启用 1.禁用
	 */
	public void setIsenable(Integer isenable) {
		this.isenable = isenable;
	}
	/**
	 * 获取：0,启用 1.禁用
	 */
	public Integer getIsenable() {
		return isenable;
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
	 * 
	 * @Title: setAllVendors 
	 * @Description: 用户对应的角色（只要一个即可）是否有所有供应商数据权限
	 * @param allVendors
	 * @return void    返回类型 
	 * @throws
	 */
	public void setAllVendors(boolean allVendors) {
        this.allVendors = allVendors;
    }
	/**
	 * 
	 * @Title: isAllVendors 
	 * @Description: 获取用户是否有所有供应商数据权限
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean isAllVendors() {
        return allVendors;
    }
	/**
	 * 
	 * @Title: setAllMaterials 
	 * @Description: 用户对应的角色（只要一个即可）是否有所有材料数据权限
	 * @param allMaterials    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void setAllMaterials(boolean allMaterials) {
        this.allMaterials = allMaterials;
    }
	/**
	 * 
	 * @Title: isAllMaterials 
	 * @Description: 获取用户是否有所有材料数据权限
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean isAllMaterials() {
        return allMaterials;
    }
}
