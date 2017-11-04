package com.heshan.springboot.web.platform.modules.sys.controller;

import com.heshan.springboot.web.platform.common.utils.QueryObject;
import com.heshan.springboot.web.platform.modules.sys.service.SysLogService;
import com.heshan.springboot.web.platform.modules.sys.entity.SysLogEntity;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.common.utils.PageUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 系统日志
 * 
 * @author
 * @email
 * @date 2017-03-08 10:40:56
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public PageResult list(@RequestParam Map<String, Object> params){
		//查询列表数据
		QueryObject query = new QueryObject(params);
		List<SysLogEntity> sysLogList = sysLogService.queryList(query);
		int total = sysLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysLogList, total, query.getLimit(), query.getPage());
		return PageResult.ok().put("page", pageUtil);
	}
	
}
