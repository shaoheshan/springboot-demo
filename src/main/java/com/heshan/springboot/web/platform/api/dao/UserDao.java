package com.heshan.springboot.web.platform.api.dao;

import com.heshan.springboot.web.platform.api.entity.UserEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 * 
 * @author
 * @email
 * @date 2017-03-23 15:22:06
 */
@Mapper
public interface UserDao extends IBaseDao<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
