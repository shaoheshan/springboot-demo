package com.heshan.springboot.web.platform.api.controller;

import java.util.Map;

import com.heshan.springboot.web.platform.api.service.TokenService;
import com.heshan.springboot.web.platform.api.service.UserService;
import com.heshan.springboot.web.platform.common.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.platform.api.annotation.AuthIgnore;
import com.heshan.springboot.web.platform.common.utils.PageResult;

/**
 * API登录授权
 *
 * @author
 * @email
 * @date 2017-07-23 15:31
 */
@RestController
@RequestMapping("/api")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     */
    @AuthIgnore
    @RequestMapping("login")
    public PageResult login(String mobile, String password) {
        Assert.hasBlank("手机号不能为空", mobile);
        Assert.hasBlank("密码不能为空", password);

        // 用户登录
        long userId = userService.login(mobile, password);

        // 生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return PageResult.ok(map);
    }

}
