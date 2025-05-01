package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.util;

import java.time.Duration;
import org.springframework.http.ResponseCookie;

public class CookieFactory {

    public static ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .secure(true)
                .domain(".morak.site")
                .maxAge(Duration.ofHours(2))
                .build();
    }
}
