package com.xtm.config;

import io.swagger.models.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author:藏剑
 * @date:2019/8/25 14:50
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    /**
     * @Description:swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xtm"))
                /*       .apis(RequestHandlerSelectors.withClassAnnotation(Controller.class))*/
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @Description: 构建 api文档的信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("用户中心后端api接口文档")
                // 描述
                .description("欢迎访问用户中心接口文档，这里是描述信息")
                // 定义版本号
                .version("1.0").build();
    }
}
