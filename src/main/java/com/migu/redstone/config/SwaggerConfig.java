/**
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company MiGu Co., Ltd.
 */
package com.migu.redstone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* 类作用:    SwaggerConfig
* 项目名称:   cmbs-create-order
* 包:       com.migu.redstone.order.config
* 类名称:    SwaggerConfig
* 类描述:    cmbs-query-order服务的swagger
* 创建人:    jianghao
* 创建时间:   2018年11月2日 下午3:22:58
*/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 营销中心cmbs-query-order API
     * @return ApiInfo
     */
    ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("营销中心cmbs-query-order API")
                .description("营销中心cmbs-query-order原子服务").termsOfServiceUrl("").version("1.0.0")
                .contact(new Contact("", "", "")).build();
    }

    /**
     * Bean.
      *〈一句话功能简述〉
      *〈功能详细描述〉
      * @return mktConfigImplementation
     */
    @Bean
    public Docket mktConfigImplementation() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).build()
            .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
            .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class).apiInfo(apiInfo());
    }

}