package com.heshan.springboot.web.platform.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.heshan.springboot.web.platform.common.constant.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heshan.springboot.web.platform.api.dao.TokenDao;
import com.heshan.springboot.web.platform.api.entity.TokenEntity;
import com.heshan.springboot.web.platform.api.service.TokenService;
import com.heshan.springboot.web.platform.config.VhscProperties;
import com.heshan.springboot.web.platform.oauth2.TokenGenerator;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private VhscProperties vhscProperties;

    @Override
    public TokenEntity queryByUserId(Long userId) {
        return tokenDao.queryByUserId(userId);
    }

    @Override
    public TokenEntity queryByToken(String token) {
        return tokenDao.queryByToken(token);
    }

    @Override
    public void save(TokenEntity token) {
        tokenDao.save(token);
    }

    @Override
    public void update(TokenEntity token) {
        tokenDao.update(token);
    }

    @Override
    public Map<String, Object> createToken(long userId) {
        // 生成一个token
        String access_token = TokenGenerator.generateValue();
        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + vhscProperties.getAuthorizationExpireTime() * 1000);
        // 判断是否生成过token
        TokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(access_token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 保存token
            save(tokenEntity);
        } else {
            tokenEntity.setToken(access_token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 更新token
            update(tokenEntity);
        }

        Map<String, Object> map = new HashMap<>();
        map.put(Const.ACCESS_TO_KEN_KEY, access_token);
        map.put("expire", vhscProperties.getAuthorizationExpireTime());

        return map;
    }
}
