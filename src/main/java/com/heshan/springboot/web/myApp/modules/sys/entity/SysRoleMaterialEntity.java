package com.heshan.springboot.web.myApp.modules.sys.entity;

import java.io.Serializable;

/**
 * 角色与材料关系（数据权限）表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-08 16:57:37
 */
public class SysRoleMaterialEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private Long roleId;
    // 医院材料编码
    private String materialCode;
    // 医院材料名称
    private String materialName;
    // 医院id
    private String orgId;
    //
    private Long createTime;
    //
    private Long createUser;
    
    
	//业务字段 , 是否选中
	private Integer checked;
	//业务字段 , 是否选中
	private String isLeaf;
	//业务字段 , 是否选中
	private Integer isParent;
	//业务字段 , 是否选中
	private String typeCode;
	// 是否是材料
    private Integer isMaterial;

    public TreeJson toTree() {
    	TreeJson tree = new TreeJson();
		tree.setId(this.getMaterialCode());
		tree.setLabel(this.getMaterialName());
		tree.setPid(this.getTypeCode());
		tree.setIsLeaf(this.getIsLeaf());
		tree.setParent((this.getIsParent() == null || this.getIsParent() == 0) ? true : false);
		tree.setChecked((this.getChecked() == null || this.getChecked() == 0) ? false : true);
		tree.setMaterial(this.getIsMaterial() == 1 ? true : false);
		return tree;
	}

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
     * 设置：医院材料编码
     */
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    /**
     * 获取：医院材料编码
     */
    public String getMaterialCode() {
        return materialCode;
    }

    /**
     * 设置：医院材料名称
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /**
     * 获取：医院材料名称
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * 设置：医院id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取：医院id
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

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}



	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

    public Integer getIsMaterial() {
        return isMaterial;
    }

    public void setIsMaterial(Integer isMaterial) {
        this.isMaterial = isMaterial;
    }

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	
}
