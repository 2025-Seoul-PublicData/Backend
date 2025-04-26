package com.example.seoulpublicdata2025backend.global.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // URL 확인 후에 수정할 수 있음
    private static final String LOCAL_REACT_CLIENT_URL = "http://localhost:5173";
    private static final String REACT_CLIENT_URL = "http://172.16.21.135:5173";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(LOCAL_REACT_CLIENT_URL, REACT_CLIENT_URL)
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
