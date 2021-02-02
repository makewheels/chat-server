package com.eg.chatserver.etc.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author makewheels
 * @Time 2021.01.30 00:53:39
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] swaggerExcludePatterns = new String[]{
                "/swagger-resources/**", "/webjars/**", "/swagger-ui.html/**", "/api",
                "/api-docs", "/api-docs/**", "/v2/api-docs", "/v2/api-docs/**", "/doc.html**",
                "/error", "/favicon.ico"};
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(swaggerExcludePatterns)
                .excludePathPatterns("/app/checkVersion")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/checkLoginToken")
                .excludePathPatterns("/oss/aliyunCallback")
        ;
    }
}
