package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private Long kakaoId;   // kakao에서 받아온 ID
    private String nickname;
    private String location;
    private String role;    // CONSUMER 또는 CORPORATE
    private String profileImageUrl;
}
