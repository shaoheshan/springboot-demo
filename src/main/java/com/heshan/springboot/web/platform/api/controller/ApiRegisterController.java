package com.heshan.springboot.web.platform.api.controller;

import com.heshan.springboot.web.platform.api.service.UserService;
import com.heshan.springboot.web.platform.common.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.platform.api.annotation.AuthIgnore;
import com.heshan.springboot.web.platform.common.utils.PageResult;

/**
 * 注册
 * 
 * @author
 * @email
 * @date 2017-07-26 17:27
 */
@RestController
@RequestMapping("/api")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     */
    @AuthIgnore
    @RequestMapping("register")
    public PageResult register(String mobile, String password) {
        Assert.hasBlank("手机号不能为空",mobile);
        Assert.hasBlank("密码不能为空",password);

        userService.save(mobile, password);

        return PageResult.ok();
    }
}
