package com.heshan.springboot.web.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author Frank
 * @version V2.0
 * @Title: RestTemplateConfig.java
 * @Package com.heshan.springboot.web.platform.config
 * @Description: Spring RestTemplate, 使用java访问URL更加优雅，更加方便。
 * @Company www.flyheze.top
 * @date 2017年8月31日 下午7:04:30
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate rest = new RestTemplate(factory) ;
        rest.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return rest;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);// ms
        factory.setConnectTimeout(15000);// ms
        return factory;
    }
}
