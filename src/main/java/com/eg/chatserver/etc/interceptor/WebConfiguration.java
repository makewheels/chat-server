package com.eg.chatserver.etc.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author makewheels
 * @Time 2021.01.30 00:53:39
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] swaggerExcludePatterns = new String[]{
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                "/api", "/api-docs", "/api-docs/**", "/doc.html**", "/error","/favicon.ico"};
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(swaggerExcludePatterns)
                .excludePathPatterns()
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/login")
        ;
    }
}
