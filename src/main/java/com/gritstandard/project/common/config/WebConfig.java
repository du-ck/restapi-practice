package com.gritstandard.project.common.config;

import com.gritstandard.project.common.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * LogInterceptor 추가를 위한 Config
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Interceptor를 등록
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");
    }
}
