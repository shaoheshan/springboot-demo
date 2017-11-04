package com.heshan.springboot.web.platform.common.enums;
/**   
 * @Title: DataPermissionKey.java 
 * @Package com.heshan.springboot.web.myApp.modules.sys.enums
 * @Description: 用户的数据权限(供应商、材料) 
 * @author Carry   
 * @Company www.flyheze.top
 * @date 2017年8月10日 下午2:26:09 
 * @version V2.0
 */
public enum DataPermSqlKey {

    vendorDataSql("供应商数据权限SQL"), materialDataSql("材料数据权限SQL");
    
    private String des;
    
    private DataPermSqlKey(String text) {
        this.des = text;
    }
    
    public String getDes() {
        return des;
    }
}

