package com.heshan.springboot.web.platform.common.enums;

/**
 * 
 * @ClassName: DataPermission 
 * @Description: 用户的数据权限(供应商、材料) 
 * @author Carry
 * @date 2017年8月14日 下午1:36:24 
 *
 */
public enum DataPermission {

    Vendor("供应商"), Material("材料");
    
    private String des;
    
    private DataPermission(String text) {
        this.des = text;
    }
    
    public String getDes() {
        return des;
    }
}

