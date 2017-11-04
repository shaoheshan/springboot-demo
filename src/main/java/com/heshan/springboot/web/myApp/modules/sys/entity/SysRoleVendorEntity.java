package com.heshan.springboot.web.myApp.modules.sys.entity;

import java.io.Serializable;

/**
 * 角色与供应商关系（数据权限）表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-08 16:57:37
 */
public class SysRoleVendorEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private Long roleId;
    // 供应商在医院的编码
    private String venCode;
    // 供应商名称
    private String venName;
    // 医院id
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
     * 设置：供应商在医院的编码
     */
    public void setVenCode(String venCode) {
        this.venCode = venCode;
    }

    /**
     * 获取：供应商在医院的编码
     */
    public String getVenCode() {
        return venCode;
    }

    /**
     * 设置：供应商名称
     */
    public void setVenName(String venName) {
        this.venName = venName;
    }

    /**
     * 获取：供应商名称
     */
    public String getVenName() {
        return venName;
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
}
