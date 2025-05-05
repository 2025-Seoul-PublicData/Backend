package com.example.seoulpublicdata2025backend.global.auth.springsecurity;

import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtParser;
import com.example.seoulpublicdata2025backend.global.exception.ErrorResponse;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> EXCLUDE_PATTERNS = List.of(
            "/auth/login/kakao",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/reviews/public/**",
            "/company/public/**",
            "/story/public/**",
            "/support/public"
    );

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (EXCLUDE_PATTERNS.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = resolveToken(request);
            if (token != null && jwtParser.validationToken(token)) {
                UsernamePasswordAuthenticationToken authentication = jwtParser.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new JwtException("Token is null or invalid");
            }
            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TOKEN);
            response.setStatus(errorResponse.getStatus());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(toJson(errorResponse));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDE_PATTERNS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String toJson(ErrorResponse errorResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(errorResponse);
    }

}
