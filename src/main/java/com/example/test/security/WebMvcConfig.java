package com.example.test.security;

import com.example.test.config.interceptor.PresetAttributeInterceptor;
import com.example.test.config.interceptor.RequestLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 统一注册纯RequestMapping跳转View的Controller
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/").setViewName("/home");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLogInterceptor());
        //registry.addInterceptor(new PresetAttributeInterceptor());
        //AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
        //registry.addInterceptor(authenticationInterceptor).addPathPatterns("/cms/**");
    }
}
