package com.heshan.springboot.web.myApp.modules.sys.controller;

import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.platform.common.utils.QueryObject;
import com.heshan.springboot.web.platform.support.AbstractCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.myApp.modules.sys.entity.SysRoleVendorEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysRoleVendorService;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.common.utils.PageUtils;

/**
 * 角色与供应商关系（数据权限）表
 * 
 * @author autoGenerator
 * @email Frank@gmail.com
 * @date 2017-08-08 16:57:37
 */
@RestController
@RequestMapping("sysrolevendor")
public class SysRoleVendorController extends AbstractCtrl {
    @Autowired
    private SysRoleVendorService sysRoleVendorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public PageResult list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        QueryObject query = new QueryObject(params);

        List<SysRoleVendorEntity> sysRoleVendorList = sysRoleVendorService.queryList(query);
        int total = sysRoleVendorService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysRoleVendorList, total, query.getLimit(), query.getPage());

        return PageResult.ok().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public PageResult info(@PathVariable("id") Long id) {
        SysRoleVendorEntity sysRoleVendor = sysRoleVendorService.queryObject(id);

        return PageResult.ok().put("sysRoleVendor", sysRoleVendor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public PageResult save(@RequestBody SysRoleVendorEntity sysRoleVendor) {
        sysRoleVendorService.save(sysRoleVendor);

        return PageResult.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public PageResult update(@RequestBody SysRoleVendorEntity sysRoleVendor) {
        sysRoleVendorService.update(sysRoleVendor);

        return PageResult.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public PageResult delete(@RequestBody Long[] ids) {
        sysRoleVendorService.deleteBatch(ids);

        return PageResult.ok();
    }

}
