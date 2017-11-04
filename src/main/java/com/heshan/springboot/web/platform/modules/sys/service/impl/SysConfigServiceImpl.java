package com.heshan.springboot.web.platform.modules.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.heshan.springboot.web.platform.common.exception.BizException;
import com.heshan.springboot.web.platform.common.utils.HttpUtil;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import com.heshan.springboot.web.platform.modules.sys.dao.SysConfigDao;
import com.heshan.springboot.web.platform.modules.sys.entity.SysConfigEntity;
import com.heshan.springboot.web.platform.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
    private static final Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);

	@Autowired
	private SysConfigDao sysConfigDao;
	@Value("${vhsc.api.api4.host}")
    private String apiUrl;
	@Value("${vhsc.api.api4.barCodeRule}")
	private String barCodeRuleUrl;
	
	@Override
	@Transactional
	public void save(SysConfigEntity config) {
		sysConfigDao.save(config);
	}

	@Override
	public void update(SysConfigEntity config) {
		sysConfigDao.update(config);
	}

	@Override
	public PageResult updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
		
        String url = apiUrl + barCodeRuleUrl;
        /* BarCodeRuleNotify  barCode = new  BarCodeRuleNotify();
         BarCodeRuleDto   barCodeDto = new BarCodeRuleDto();
         barCodeDto.setRule(value);
         barCode.setData(barCodeDto);*/
        try {
            String jsonParams = JSON.toJSONString("");
            //String replace = jsonParams.replaceAll("\\\\","");
            logger.info("条码配置接口发送======>url:" + url + ",接口发送" +jsonParams);
            String postBody = HttpUtil.doPostJson(url, jsonParams);
            logger.info("条码配置接口返回<=======url:" + url + ",返回内容" + postBody);
            JSONObject object = JSONObject.parseObject(postBody);
            return PageResult.setResult(object.getInteger("code"), object.getString("msg"));
        } catch (Exception e) {
        	
            logger.error("条码配置异常！msg:" + e.getMessage(), e);
            return PageResult.error(500, "调用接口失败");
        }
		
		
	}

	@Override
	public void deleteBatch(Long[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	@Override
	public List<SysConfigEntity> queryList(Map<String, Object> map) {
		return sysConfigDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	@Override
	public SysConfigEntity queryObject(Long id) {
		return sysConfigDao.queryObject(id);
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		return value;
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key, null);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BizException("获取参数失败");
		}
	}
}
