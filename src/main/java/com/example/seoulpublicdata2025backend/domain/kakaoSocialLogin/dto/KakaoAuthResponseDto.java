package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import lombok.Getter;

@Getter
public class KakaoAuthResponseDto {

    private final String memberStatus;

    private KakaoAuthResponseDto(MemberStatus memberStatus) {
        this.memberStatus = memberStatus.getValue();
    }

    public static KakaoAuthResponseDto of(MemberStatus memberStatus) {
        return new KakaoAuthResponseDto(memberStatus);
    }
}
