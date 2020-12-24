//package com.ssz.common.web.configuration;
//
//
//import com.ssz.common.web.util.HttpUtils;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMethod;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static com.google.common.collect.Lists.newArrayList;
//
//@Configuration
//@EnableSwagger2
//@ConfigurationProperties(prefix = "spring.swagger")
//@Setter
//@Getter
//@ConditionalOnExpression("${spring.swagger.enabled: true}")
//public class SwaggerConfiguration {
//
//    /**
//     * 是否启用 swagger
//     */
//    private Boolean enabled;
//    private String title;
//    private String desc;
//    private String version;
//    private String termsOfServiceUrl;
//    private String license;
//    private String licenseUrl;
//    private String basePackage;
//    private String groupName;
//    private String contactName;
//    private String contactUrl;
//    private String contactEmail;
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .groupName(groupName)
//                .directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, customerResponseMessage())
//                .globalResponseMessage(RequestMethod.POST, customerResponseMessage())
//                .globalResponseMessage(RequestMethod.PUT, customerResponseMessage())
//                .globalResponseMessage(RequestMethod.DELETE, customerResponseMessage())
//                .forCodeGeneration(true)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(basePackage)) //这里写的是API接口所在的包位置
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
//    }
//
//    private List<ApiKey> securitySchemes() {
//        return newArrayList(
//                new ApiKey(HttpUtils.HEADER_AUTHORIZATION, HttpUtils.HEADER_AUTHORIZATION, "header"));
//    }
//
//    private List<SecurityContext> securityContexts() {
//        return newArrayList(
//                SecurityContext.builder()
//                        .securityReferences(defaultAuth())
//                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
//                        .build()
//        );
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return newArrayList(
//                new SecurityReference(HttpUtils.HEADER_AUTHORIZATION, authorizationScopes));
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title(title)
//                .description(desc)
//                .version(version)
//                .termsOfServiceUrl(termsOfServiceUrl)
//                .license(license)
//                .licenseUrl(licenseUrl)
//                .contact(new Contact(contactName, contactUrl, contactEmail))
//                .build();
//    }
//
//    private List<ResponseMessage> customerResponseMessage() {
//        return newArrayList(
//                /*new ResponseMessageBuilder()
//                        .code(HttpStatus.BAD_REQUEST.value()).message(HttpStatus.BAD_REQUEST.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global400ErrorRespModel)"))
//                        .build(),
//                new ResponseMessageBuilder()
//                        .code(HttpStatus.UNAUTHORIZED.value()).message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global401ErrorRespModel)"))
//                        .build(),
//                *//*new ResponseMessageBuilder()
//                        .code(HttpStatus.FORBIDDEN.value()).message(HttpStatus.FORBIDDEN.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global403ErrorRespModel)"))
//                        .build(),*//*
//                new ResponseMessageBuilder()
//                        .code(HttpStatus.NOT_FOUND.value()).message(HttpStatus.NOT_FOUND.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global404ErrorRespModel)"))
//                        .build(),*/
//                /*new ResponseMessageBuilder()
//                        .code(HttpStatus.METHOD_NOT_ALLOWED.value()).message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global405ErrorRespModel)"))
//                        .build(),
//                new ResponseMessageBuilder()
//                        .code(HttpStatus.NOT_ACCEPTABLE.value()).message(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global406ErrorRespModel)"))
//                        .build(),*/
//                /*new ResponseMessageBuilder()
//                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global500ErrorRespModel)"))
//                        .build(),
//                new ResponseMessageBuilder()
//                        .code(HttpStatus.BAD_GATEWAY.value()).message(HttpStatus.BAD_GATEWAY.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global502ErrorRespModel)"))
//                        .build(),*/
//                /*new ResponseMessageBuilder()
//                        .code(HttpStatus.SERVICE_UNAVAILABLE.value()).message(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global503ErrorRespModel)"))
//                        .build(),*/
//                /*new ResponseMessageBuilder()
//                        .code(HttpStatus.GATEWAY_TIMEOUT.value()).message(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase())
//                        .responseModel(new ModelRef("全局错误响应(Global504ErrorRespModel)"))
//                        .build()*/
//        );
//    }
//
//}
//
//
