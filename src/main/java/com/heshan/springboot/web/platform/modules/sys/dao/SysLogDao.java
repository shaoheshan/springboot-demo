package com.heshan.springboot.web.platform.modules.sys.dao;

import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.heshan.springboot.web.platform.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 * 
 * @author
 * @email
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysLogDao extends IBaseDao<SysLogEntity> {
	
}
