package com.example.seoulpublicdata2025backend.global.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    public static Long getCurrentMemberKakaoId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증된 사용자가 없습니다.");
        }

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return Long.valueOf(userDetails.getUsername());
        }

        throw new IllegalStateException("UserDetails가 아닙니다.");
    }


}
