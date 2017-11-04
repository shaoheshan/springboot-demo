package com.heshan.springboot.web.platform.api.dao;

import com.heshan.springboot.web.platform.api.entity.TokenEntity;
import com.heshan.springboot.web.platform.support.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Token
 * 
 * @author
 * @email
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface TokenDao extends IBaseDao<TokenEntity> {
    
    TokenEntity queryByUserId(Long userId);

    TokenEntity queryByToken(String token);
	
}
