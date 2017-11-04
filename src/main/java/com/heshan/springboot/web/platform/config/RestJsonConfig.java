package com.heshan.springboot.web.platform.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * @Title: RestTplConfig.java
 * @Package com.heshan.springboot.web.platform.config
 * @Description: 使用FastJson作为JSON序列化工具
 * @author Frank@gmail.com
 * @Company www.flyheze.top
 * @date 2017年8月11日 上午9:55:50
 * @version V2.0
 */
@Configuration
@ConditionalOnClass(JSON.class)
@ConditionalOnMissingBean(FastJsonHttpMessageConverter.class)
@ConditionalOnWebApplication
public class RestJsonConfig {

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastjsonConfig());
        converter.setSupportedMediaTypes(getSupportedMediaType());
        return converter;
    }

    /**
     * fastjson的配置
     */
    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // fastjson SerializerFeature属性：http://blog.csdn.net/u010246789/article/details/52539576
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        // 格式化JSON数据，由于JSON会打印在日志中，不让换行所以不格式化
        // fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);
        // 如果出现日期Date类的对象，按照设置的格式进行处理
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ValueFilter valueFilter = new ValueFilter() {
            public Object process(Object o, String s, Object o1) {
                if (null == o1) {
                    o1 = "";
                }
                return o1;
            }
        };
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        fastJsonConfig.setSerializeFilters(valueFilter);
        return fastJsonConfig;
    }

    /**
     * 支持的mediaType类型
     */
    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        return mediaTypes;
    }

    @Bean
    @ConditionalOnMissingBean({ClientHttpRequestFactory.class})
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(3000);// ms
        factory.setConnectTimeout(3000);// ms
        return factory;
    }

}
