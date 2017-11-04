package com.heshan.springboot.web.platform.api.controller;

import com.heshan.springboot.web.platform.api.annotation.AuthIgnore;
import com.heshan.springboot.web.platform.api.annotation.LoginUser;
import com.heshan.springboot.web.platform.api.entity.TokenEntity;
import com.heshan.springboot.web.platform.api.entity.UserEntity;
import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.common.utils.PageResult;

import org.springframework.web.bind.annotation.*;

/**
 * API测试接口
 *
 * @author
 * @email
 * @date 2017-07-23 15:47
 */
@RestController
@RequestMapping("/api")
public class ApiTestController {

    /**
     * 获取用户信息
     */
    @GetMapping("userInfo")
    public PageResult userInfo(@LoginUser UserEntity user) {
        return PageResult.ok().put("user", user);
    }

    /**
     * 忽略Token验证测试
     */
    @AuthIgnore
    @GetMapping("notToken")
    public PageResult notToken() {
        return PageResult.ok().put("msg", "无需access_token也能访问。。。");
    }

    /**
     * 接收JSON数据
     */
    @PostMapping("jsonData")
    public PageResult jsonData(@LoginUser UserEntity user, @RequestBody TokenEntity token) {
        return PageResult.ok().put("user", user).put(Const.ACCESS_TO_KEN_KEY, token);
    }
}
