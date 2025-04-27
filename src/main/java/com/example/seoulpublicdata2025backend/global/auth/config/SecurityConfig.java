package com.example.seoulpublicdata2025backend.global.auth.config;

import com.example.seoulpublicdata2025backend.global.auth.springsecurity.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/reviews/public/**").permitAll()
                            .requestMatchers("/company/preview").permitAll()
                            .requestMatchers("/auth/login/kakao",
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-resources/**",
                                    "/webjars/**")
                            .permitAll()
                            .requestMatchers("/member/signup", "/reviews/**")
                            .hasRole("PRE_MEMBER")
                            .anyRequest()
                            .authenticated();

                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
