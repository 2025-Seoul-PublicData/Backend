package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type;

public enum MemberStatus {
    MEMBER("회원"),
    NOT_MEMBER("비회원");

    private final String value;

    MemberStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
