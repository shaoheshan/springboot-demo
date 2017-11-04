package com.heshan.springboot.web.platform.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.platform.common.annotation.SysLog;
import com.heshan.springboot.web.platform.common.validator.ValidatorUtils;
import com.heshan.springboot.web.platform.modules.sys.service.SysConfigService;
import com.heshan.springboot.web.platform.support.AbstractCtrl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.platform.modules.sys.entity.SysConfigEntity;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.common.utils.PageUtils;
import com.heshan.springboot.web.platform.common.utils.QueryObject;

import io.swagger.annotations.ApiOperation;

/**
 * 系统配置信息
 * 
 * @author
 * @email
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractCtrl {
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public PageResult list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        QueryObject query = new QueryObject(params);
        List<SysConfigEntity> configList = sysConfigService.queryList(query);
        int total = sysConfigService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());

        return PageResult.ok().put("page", pageUtil);
    }

    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public PageResult info(@PathVariable("id") Long id) {
        SysConfigEntity config = sysConfigService.queryObject(id);

        return PageResult.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public PageResult save(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.save(config);

        return PageResult.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public PageResult update(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.update(config);

        return PageResult.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public PageResult delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);

        return PageResult.ok();
    }
    
    /*
     * 读取条码设置的配置
     * */
    @RequestMapping("/barCodeStes")
    @ApiOperation(value = "读取条码设置的配置")
    public PageResult barCodeStes(@RequestParam Map<String, Object> params) {
    	//条码管理专属Key值
    	//String key = "BARCODESTES";
    	String key = (String) params.get("key");
    	Map<String, Object> map = new HashMap<>();
    	//value 有值返回value值 , 没值返回{barCodeType: 0}
    	String  str= sysConfigService.getValue(key,"{\"barCodeType\": 0}");
        map.put("map", str);
        return PageResult.ok().put("map", map);
    }
    
    /*
     * 保存条码设置的配置
     * */
    @RequestMapping("/updateBarCodeStes")
    @ApiOperation(value = "保存条码设置的配置 ")
    public PageResult updateBarCodeStes(@RequestBody SysConfigEntity config) {
    	
        ValidatorUtils.validateEntity(config);
        String key = config .getKey();
        String value =config.getValue();
        PageResult updateValueByKey = sysConfigService.updateValueByKey(key,value);

        return PageResult.ok(updateValueByKey);
    }

}
