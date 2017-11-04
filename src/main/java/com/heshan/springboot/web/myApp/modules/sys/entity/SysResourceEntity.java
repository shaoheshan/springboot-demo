package com.heshan.springboot.web.myApp.modules.sys.entity;

import java.io.Serializable;


/**
 * 菜单资源表
 *
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-23 14:24:54
 */
public class SysResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //名称
    private String name;
    //菜单编码
    private String code;
    //菜单url地址
    private String url;
    //排序 从0，1，2开始
    private Integer orderby;
    //菜单类型：0目录；1菜单；2按钮
    private Integer menuType;
    //
    private Long parentId;
    //1,启用 0.禁用
    private Integer isenable;
    //默认为1 1:有效数据 0:逻辑删除数据
    private Integer status;
    //图标
    private String imgUrl;
    //是否区分两端三端 0 不需要 1需要
    private Integer hasModel;

    //业务字段 , 是否选中
    private Integer checked;


    public TreeJson toTree() {
        TreeJson tree = new TreeJson();
        tree.setId(String.valueOf(this.getId()));
        tree.setPid(String.valueOf(this.parentId));
        tree.setLabel(this.getName());
        tree.setUrl(this.getUrl());
        tree.setImgUrl(this.getImgUrl());
        tree.setDisabled((this.getIsenable() == null || this.getIsenable() == 0) ? true : false);
        tree.setChecked((this.getChecked() == null || this.getChecked() == 0) ? false : true);
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
     * 设置：名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：菜单编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取：菜单编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置：菜单url地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取：菜单url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置：排序 从0，1，2开始
     */
    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    /**
     * 获取：排序 从0，1，2开始
     */
    public Integer getOrderby() {
        return orderby;
    }

    /**
     * 设置：菜单类型：0目录；1菜单；2按钮
     */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取：菜单类型：0目录；1菜单；2按钮
     */
    public Integer getMenuType() {
        return menuType;
    }

    /**
     * 设置：
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：
     */
    public Long getParentId() {
        return parentId;
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
     * 设置：图标
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取：图标
     */
    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

	public Integer getHasModel() {
		return hasModel;
	}

	public void setHasModel(Integer hasModel) {
		this.hasModel = hasModel;
	}

	

}
