package com.ylhl.phlab.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Profile({"dev","test"})
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket creat(){
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder builder = new ParameterBuilder();
        builder.name("token").modelRef(new ModelRef("string")).parameterType("header").required(false);
        parameters.add(builder.build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("临平民政开发文档")
                .description("易连科技")
                .termsOfServiceUrl("http://lpmz.elianzj.cn/swagger-ui.html")
                .version("1.0")
                .build();
    }
}
