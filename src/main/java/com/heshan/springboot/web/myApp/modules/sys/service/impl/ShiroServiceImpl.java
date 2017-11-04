package com.heshan.springboot.web.myApp.modules.sys.service.impl;

import java.util.*;

import com.heshan.springboot.web.myApp.modules.sys.dao.SysMenuDao;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserDao;
import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserTokenDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysMenuEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserTokenEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.ShiroService;
import com.heshan.springboot.web.myApp.modules.sys.service.SysRoleService;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserEntity;
import com.heshan.springboot.web.platform.config.VhscProperties;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heshan.springboot.web.myApp.modules.sys.service.SysUserRoleService;
import com.heshan.springboot.web.platform.common.utils.Constant;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private VhscProperties vhscProperties;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.queryList(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        SysUserEntity user = sysUserDao.queryObject(userId);
        // 获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        if (user != null && CollectionUtils.isNotEmpty(roleIdList)) {
            user.setRoleIdList(roleIdList);
            roleIdList.forEach(roleId -> {
                SysRoleEntity sysRole = sysRoleService.queryObject(roleId);
                Integer allVendors = sysRole.getAllVendors();
                Integer allMaterials = sysRole.getAllMaterials();
                if (allVendors == 1) {
                    user.setAllVendors(true);
                }
                if (allMaterials == 1) {
                    user.setAllMaterials(true);
                }
            });
        }
        return user;
    }

    @Override
    public int updateTokenExpireTime(String token, Long userId) {
        SysUserTokenEntity entity = new SysUserTokenEntity();
        entity.setToken(token);
        entity.setUserId(userId);
        entity.setUpdateTime(new Date());
        entity.setExpireTime(new Date(System.currentTimeMillis() + vhscProperties.getAuthorizationExpireTime() * 1000));
        sysUserTokenDao.update(entity);
        return 0;
    }
}
