package com.example.seoulpublicdata2025backend.global.auth.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // URL 확인 후에 수정할 수 있음
    private static final List<String> CLIENT_ORIGINS = List.of(
            "https://morak.site",
            "https://www.morak.site"
    );


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(CLIENT_ORIGINS.toArray(new String[0]))
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
