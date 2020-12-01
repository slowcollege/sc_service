package com.slow.college.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomCorsConfig {

    @Bean
    public WebMvcConfigurer getConfig(){
        return new WebMvcConfigurerAdapter() {
            // 重新找个方法
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 允许站外8081端口来访问api找个接口
                // addAllowedOrigin("*"); // 设置访问源地址 
                // addAllowedHeader("*"); // 设置访问源请求头
                // addAllowedMethod("*"); // 设置访问源请求方法
                registry.addMapping("/api/**");
            }
        };
    }

}
