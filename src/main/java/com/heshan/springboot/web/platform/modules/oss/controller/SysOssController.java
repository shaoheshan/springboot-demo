package com.heshan.springboot.web.platform.modules.oss.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.heshan.springboot.web.platform.common.exception.BizException;
import com.heshan.springboot.web.platform.common.utils.ConfigConstant;
import com.heshan.springboot.web.platform.common.validator.ValidatorUtils;
import com.heshan.springboot.web.platform.common.validator.group.AliyunGroup;
import com.heshan.springboot.web.platform.modules.oss.service.SysOssService;
import com.heshan.springboot.web.platform.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.heshan.springboot.web.platform.common.utils.Constant;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.common.utils.PageUtils;
import com.heshan.springboot.web.platform.common.utils.QueryObject;
import com.heshan.springboot.web.platform.common.validator.group.QcloudGroup;
import com.heshan.springboot.web.platform.common.validator.group.QiniuGroup;
import com.heshan.springboot.web.platform.modules.oss.cloud.CloudStorageConfig;
import com.heshan.springboot.web.platform.modules.oss.cloud.OSSFactory;
import com.heshan.springboot.web.platform.modules.oss.entity.SysOssEntity;

/**
 * 文件上传
 * 
 * @author
 * @email
 * @date 2017-03-25 12:13:26
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public PageResult list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        QueryObject query = new QueryObject(params);
        List<SysOssEntity> sysOssList = sysOssService.queryList(query);
        int total = sysOssService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysOssList, total, query.getLimit(), query.getPage());

        return PageResult.ok().put("page", pageUtil);
    }

    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public PageResult config() {
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return PageResult.ok().put("config", config);
    }

    /**
     * 保存云存储配置信息
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public PageResult saveConfig(@RequestBody CloudStorageConfig config) {
        // 校验类型
        ValidatorUtils.validateEntity(config);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            // 校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            // 校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            // 校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

        return PageResult.ok();
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public PageResult upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new BizException("上传文件不能为空");
        }

        // 上传文件
        String url = OSSFactory.build().upload(file.getBytes());

        // 保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        sysOssService.save(ossEntity);

        return PageResult.ok().put("url", url);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public PageResult delete(@RequestBody Long[] ids) {
        sysOssService.deleteBatch(ids);

        return PageResult.ok();
    }

}
