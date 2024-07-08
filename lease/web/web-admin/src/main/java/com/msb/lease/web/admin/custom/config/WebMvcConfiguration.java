package com.msb.lease.web.admin.custom.config;

import com.msb.lease.web.admin.intercepotor.AuthenticationInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 实现接口WebMvcConfigurer可以向SpringMvc容器中加入自定义的相关组件
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
//    @Resource
//    private StringToItemTypeConverter stringToItemTypeConverter;
    @Resource
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    /**
     * 将自定义的类型转换器注册到SpringMVC中
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(this.stringToItemTypeConverter);
       registry.addConverterFactory(this.stringToBaseEnumConverterFactory);

    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns拦截路径---------exckudePathPatterns放行
        registry.addInterceptor(this.authenticationInterceptor)
                .addPathPatterns("/admin/**")//需要拦截的路径
                .excludePathPatterns("/admin/login/**");//需要放行的路径
    }
}
