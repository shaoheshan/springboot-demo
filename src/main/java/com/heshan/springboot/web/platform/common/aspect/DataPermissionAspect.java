package com.heshan.springboot.web.platform.common.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.platform.common.annotation.RequiresDataPermission;
import com.heshan.springboot.web.platform.common.enums.DataPermSqlKey;
import com.heshan.springboot.web.platform.common.enums.DataPermission;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**   
 * @Title: DataPermissionAspect.java 
 * @Package com.heshan.springboot.web.platform.common.aspect
 * @Description: 数据权限切面 
 * @author Carry   
 * @Company www.flyheze.top
 * @date 2017年8月14日 下午1:52:34 
 * @version V2.0
 */
@Aspect
@Component
public class DataPermissionAspect {

    @Pointcut("@annotation(com.heshan.springboot.web.platform.common.annotation.RequiresDataPermission)")
    public void dataPointcut() {
        
    }
    
    @Around("dataPointcut()")
    public Object aroundDataPermission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        RequiresDataPermission requiresDataPermission = method.getAnnotation(RequiresDataPermission.class);
        DataPermission[] permissions = requiresDataPermission.data();
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        if (user != null && user.getIsadmin() != 1 && CollectionUtils.isNotEmpty(user.getRoleIdList())) {
            // 根据角色列表查询对应的数据权限
            for (Object arg : point.getArgs()) {
                if (arg instanceof HashMap<?, ?>) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> map = (HashMap<String, Object>)arg;
                    setUserDataPerms(map, user, permissions);
                }
            }
        }
        return point.proceed();
    }
    
    private void setUserDataPerms(HashMap<String, Object> map, SysUserEntity user, DataPermission[] permissions) {
        List<Long> roleIdList = user.getRoleIdList();
        // 设置根据角色取对应数据权限的SQL
        StringBuffer inSql = new StringBuffer("where role_id in (");
        for (int i = 0; i < roleIdList.size(); i++) {
            Long roleId = roleIdList.get(i);
            inSql.append(roleId);
            if (i < roleIdList.size() - 1) {
                inSql.append(",");
            }
        }
        inSql.append(")");
        for (DataPermission dataPermission : permissions) {
            switch (dataPermission) {
            case Vendor:
                // 角色列表对应的供应商
                if (!user.isAllVendors()) {
                    String vendorSql = "select distinct ven_code from sys_role_vendor " + inSql.toString();
                    map.put(DataPermSqlKey.vendorDataSql.name(), vendorSql);
                }
                break;
            case Material:
                // 角色列表对应的材料
                if (!user.isAllMaterials()) {
                    String materialSql = "select distinct material_code from sys_role_material " + inSql.toString();
                    map.put(DataPermSqlKey.materialDataSql.name(), materialSql);
                }
                break;
            default:
                break;
            }
        }
    }
}

