package com.demo.spring.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value(value = "${swagger.base.package.path}")
    private String basePackagePath;

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi() {
        log.info("创建swagger配置文件...");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackagePath))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息
     */
    private ApiInfo apiInfo() {
        log.info("配置swagger页面信息...");
        return new ApiInfoBuilder()
                // 页面标题
                .title("Spring Boot中使用Swagger2构建RESTful API")
                // 描述
                .description("更多Spring Boot相关文章请关注：https://swagger.io/")
                // 版本号
                .version("v.1.0")
                .termsOfServiceUrl("https://swagger.io/")
                .build();
    }
}
