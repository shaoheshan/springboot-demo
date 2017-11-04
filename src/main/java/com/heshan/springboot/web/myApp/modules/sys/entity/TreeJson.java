/**
 * @fileName: TreeJson.java
 * @package: com.rf.dtd.profileplatform.bussiness.dataprofile.testproduction.domain
 * @description: TODO
 * @author: 99fu
 * @date: 2015年8月20日 下午5:14:48
 * @version V2.0
 *
 *          Copyright (C) 2015, Richfit Information Technology Co., Ltd. All rights reserved.
 *
 */
package com.heshan.springboot.web.myApp.modules.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: TreeJson 
* @Description: 树形结构实体类
* @author wanghe
* @date 2016年11月12日 上午11:35:30 
*
 */
public class TreeJson implements Serializable {
    private static final long serialVersionUID = -7065109281837764890L;
    // 节点id
    private String id;
    // 父节点
    private String pid;
    // 编码
    private String code;
    // 节点名称
    private String label;
    // url
    private String url;
    // "iconCls":"icon-save" 按钮
    private String iconCls;
    // 选 中，关闭
    private boolean checked;
    // 是否父节点
    private boolean isParent;
    // 是否显示复选框
    private boolean nocheck;
    // 是否末级节点
    private String isLeaf;
    /** 半选 */
    private boolean halfCheck;
    // 是否禁用 true 是 false 否
    private boolean disabled;
    // 是否是材料
    private boolean isMaterial;
    // 子节点
    private List<TreeJson> children = new ArrayList<TreeJson>();
  //图标
  	private String imgUrl;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public List<TreeJson> getChildren() {
        return children;
    }

    public void setChildren(List<TreeJson> children) {
        this.children = children;
    }

	public boolean getHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(boolean halfCheck) {
		this.halfCheck = halfCheck;
	}

    public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

    public boolean isMaterial() {
        return isMaterial;
    }

    public void setMaterial(boolean isMaterial) {
        this.isMaterial = isMaterial;
    }
    
}
