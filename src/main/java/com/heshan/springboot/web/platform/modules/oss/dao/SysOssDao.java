package com.heshan.springboot.web.platform.modules.oss.dao;

import org.apache.ibatis.annotations.Mapper;

import com.heshan.springboot.web.platform.modules.oss.entity.SysOssEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;

/**
 * 文件上传
 * 
 * @author
 * @email
 * @date 2017-03-25 12:13:26
 */
@Mapper
public interface SysOssDao extends IBaseDao<SysOssEntity> {
	
}
