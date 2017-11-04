package com.heshan.springboot.web.platform.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: SwaggerConfig
 * @Description: Swagger2配置 http://localhost:8080/swagger-ui.html
 * @author liusongqing
 * @date 2017年8月11日 上午9:28:34
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASEPACKAGE = "com.heshan.springboot.web";

    @Bean
    public Docket createRestApi() {
        // 可以添加多个header或参数
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.parameterType("header").name("Authorization").defaultValue("Authorization").description("header中Authorization").modelRef(new ModelRef("string"))
                .required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(BASEPACKAGE))
                .paths(PathSelectors.any()).build().globalOperationParameters(aParameters).ignoredParameterTypes(ApiIgnore.class);
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("LiuSongqing", "http://www.flyheze.top", "liusongqing@gmail.com");
        return new ApiInfoBuilder().title("供应宝医院端2.0前台API接口").version("2.0").description("http://www.flyheze.top")
                .termsOfServiceUrl("http://www.flyheze.top").version("2.0").contact(contact).build();
    }
}
