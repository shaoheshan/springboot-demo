package com.heshan.springboot.web.myApp.modules.sys.service.impl;

import java.util.Date;

import com.heshan.springboot.web.myApp.modules.sys.dao.SysUserTokenDao;
import com.heshan.springboot.web.myApp.modules.sys.entity.SysUserTokenEntity;
import com.heshan.springboot.web.myApp.modules.sys.service.SysUserTokenService;
import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.config.VhscProperties;
import com.heshan.springboot.web.platform.oauth2.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {
    @Autowired
    private SysUserTokenDao sysUserTokenDao;
    @Autowired
    private VhscProperties vhscProperties;

    @Override
    public SysUserTokenEntity queryByUserId(Long userId) {
        return sysUserTokenDao.queryByUserId(userId);
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public void save(SysUserTokenEntity token) {
        sysUserTokenDao.save(token);
    }

    @Override
    public void update(SysUserTokenEntity token) {
        sysUserTokenDao.update(token);
    }

    @Override
    public PageResult createToken(long userId) {
        // 生成一个token
        String token = TokenGenerator.generateValue();

        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + vhscProperties.getAuthorizationExpireTime() * 1000);

        // 判断是否生成过token
        SysUserTokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 保存token
            save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 更新token
            update(tokenEntity);
        }

        PageResult r = PageResult.ok().put(Const.AUTHORIZATION_KEY, token).put("expire", vhscProperties.getAuthorizationExpireTime());

        return r;
    }
}
