package com.pm.background.common.config;

/**
 * @author Larry
 * @date 2020/5/18 0018 15:02
 * @description 配置拦截器 进行请求拦截
 */

import com.pm.background.common.enumeration.InfoEnum;
import com.pm.background.common.interceptors.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//添加配置类注解
@SpringBootConfiguration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private MyInterceptor myInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(myInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry.addResourceHandler("/file/**").addResourceLocations(InfoEnum.LOCATION.getDesc());
    }
}