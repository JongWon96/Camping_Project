package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  
public class WebConfig implements WebMvcConfigurer {
    @Value("${connect.path}")
    private String connectPath;
    @Value("${resource.path}")
    private String resourcePath;

    /*
    **  ResourceHandlerRegistry - 리소스 등록 및 핸들러 관리 클래스
    */ 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
    }
}
