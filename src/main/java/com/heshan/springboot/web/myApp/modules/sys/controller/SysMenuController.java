package com.heshan.springboot.web.myApp.modules.sys.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysMenuEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleEntity;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleResourceEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.ShiroService;
import com.heshan.springboot.web.platform.common.annotation.SysLog;
import com.heshan.springboot.web.platform.common.exception.BizException;
import com.heshan.springboot.web.platform.support.AbstractCtrl;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.myApp.modules.sys.service.SysMenuService;
import com.heshan.springboot.web.platform.common.utils.Constant.MenuType;
import com.heshan.springboot.web.platform.common.utils.PageResult;

import io.swagger.annotations.ApiOperation;

/**
 * 系统菜单
 * 
 * @author
 * @email
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractCtrl {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单
     */
    @RequestMapping("/nav")
    public PageResult nav() {
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return PageResult.ok().put("menuList", menuList).put("permissions", permissions);
    }

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<>());

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public PageResult select() {
        // 查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        // 添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return PageResult.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public PageResult info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.queryObject(menuId);
        return PageResult.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public PageResult save(@RequestBody SysMenuEntity menu) {
        // TODO 数据校验应改为jsr303
        verifyForm(menu);

        sysMenuService.save(menu);

        return PageResult.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public PageResult update(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        sysMenuService.update(menu);

        return PageResult.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public PageResult delete(long menuId) {
        if (menuId <= 31) {
            return PageResult.error("系统菜单，不能删除");
        }

        // 判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return PageResult.error("请先删除子菜单或按钮");
        }

        sysMenuService.deleteBatch(new Long[] { menuId });

        return PageResult.ok();
    }

    /**
     * 验证参数是否正确
     * 
     */
    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new BizException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new BizException("上级菜单不能为空");
        }

        // 菜单
        if (menu.getType() == MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new BizException("菜单URL不能为空");
            }
        }

        // 上级菜单类型
        int parentType = MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.queryObject(menu.getParentId());
            parentType = parentMenu.getType();
        }

        // 目录、菜单
        if (menu.getType() == MenuType.CATALOG.getValue() || menu.getType() == MenuType.MENU.getValue()) {
            if (parentType != MenuType.CATALOG.getValue()) {
                throw new BizException("上级菜单只能为目录类型");
            }
            return;
        }

        // 按钮
        if (menu.getType() == MenuType.BUTTON.getValue()) {
            if (parentType != MenuType.MENU.getValue()) {
                throw new BizException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
    
    /**
     * 所有菜单列表
     */
    @RequestMapping("/getResourceByRole")
    public PageResult getResourceByRole(@RequestBody SysRoleEntity role) {
    	return sysMenuService.getResourceByRole(role.getRoleId(),getUser());
    }
    
    /**
     * 
    * @Title: addFuncMenuPerm 
    * @Description:保存功能菜单权限
    * @param list
    * @return Result    返回类型 
    * @throws
     */
    @RequestMapping(value = "/addFuncMenuPerm",method = RequestMethod.POST)
    @ApiOperation(value = "保存功能菜单权限")
    public PageResult addFuncMenuPerm(@RequestBody List<SysRoleResourceEntity> list) {
    	Long roleId= null;
		for(SysRoleResourceEntity roList:list) {
			roleId = roList.getRoleId();
			break;
		}
			//去除重复的list元素 , hashSet , 重构eqluals 和 hashCode
		 	HashSet<SysRoleResourceEntity> h  =   new  HashSet<SysRoleResourceEntity>(list); 
		    list.clear(); 
		    list.addAll(h); 
    	return sysMenuService.addFuncMenuPerm(roleId, list,getUser());
    }
    
    /**
     * 
    *用户拥有的数据权限查询
     */
    @RequestMapping(value = "userMenuList/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "用户拥有的数据权限查询 ")
    public PageResult menuList(@PathVariable("userId") Long userId) {
    	if(userId!=null) {
    		//用户是超级用户是 , 可以拥有所有菜单
    		if(getUser().getIsadmin() == 1) {
    		//	return  sysMenuService.queryResourceList(userId);
        	}
    		//return sysMenuService.getUserResourceList(userId);
    	}
		return PageResult.error();
    }
}
