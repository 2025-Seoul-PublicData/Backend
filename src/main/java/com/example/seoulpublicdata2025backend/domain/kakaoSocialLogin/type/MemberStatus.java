package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
    MEMBER("회원"),
    PRE_MEMBER("가입 전"),
    NOT_MEMBER("비회원");

    private final String value;
}
