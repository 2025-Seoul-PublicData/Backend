package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
    MEMBER("MEMBER"),
    PRE_MEMBER("PRE_MEMBER"),
    NOT_MEMBER("NOT_MEMBER");

    private final String value;
}
