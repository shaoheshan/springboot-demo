package com.heshan.springboot.web.platform.modules.sys.dao;

import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.heshan.springboot.web.platform.modules.sys.entity.SysConfigEntity;

/**
 * 系统配置信息
 * 
 * @author
 * @email
 * @date 2016年12月4日 下午6:46:16
 */
@Mapper
public interface SysConfigDao extends IBaseDao<SysConfigEntity> {
	
	/**
	 * 根据key，查询value
	 */
	String queryByKey(String paramKey);
	
	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	
}
