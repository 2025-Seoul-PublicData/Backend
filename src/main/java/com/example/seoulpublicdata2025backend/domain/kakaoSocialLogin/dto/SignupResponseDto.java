package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private final Long memberId;

    private SignupResponseDto(Long memberId) {
        this.memberId = memberId;
    }

    public static SignupResponseDto from(Long memberId) {
        return new SignupResponseDto(memberId);
    }
}
