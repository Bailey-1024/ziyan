package com.msb.lease.web.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 接口文档配置类
 */
@Configuration
public class Knife4jConfiguration {
    /**
     * 定义接口文档的总信息
     * @return
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI().info(
                new Info()
                        .title("后台管理系统API")
                        .version("1.0")
                        .description("后台管理系统API"));
    }

    /**
     * 定义接口文档的各个分组信息
     * @return
     */
    @Bean
    public GroupedOpenApi systemAPI() {

        return GroupedOpenApi.builder().group("系统信息管理").
                pathsToMatch(
                        "/admin/system/**"
                ).
                build();
    }

    @Bean
    public GroupedOpenApi loginAPI() {

        return GroupedOpenApi.builder().group("后台登录管理").
                pathsToMatch(
                        "/admin/login/**",
                        "/admin/info"
                ).
                build();
    }

    @Bean
    public GroupedOpenApi apartmentAPI() {

        return GroupedOpenApi.builder().group("公寓信息管理").
                pathsToMatch(
                        "/admin/apartment/**",
                        "/admin/room/**",
                        "/admin/label/**",
                        "/admin/facility/**",
                        "/admin/fee/**",
                        "/admin/attr/**",
                        "/admin/payment/**",
                        "/admin/region/**",
                        "/admin/term/**",
                        "/admin/file/**"
                ).build();
    }
    @Bean
    public GroupedOpenApi leaseAPI() {
        return GroupedOpenApi.builder().group("租赁信息管理").
                pathsToMatch(
                        "/admin/appointment/**",
                        "/admin/agreement/**"
                ).build();
    }
    @Bean
    public GroupedOpenApi userAPI() {
        return GroupedOpenApi.builder().group("平台用户管理").
                pathsToMatch(
                        "/admin/user/**"
                ).build();
    }
}