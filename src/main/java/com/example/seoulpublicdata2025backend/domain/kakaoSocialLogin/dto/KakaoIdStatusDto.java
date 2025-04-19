package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoIdStatusDto {
    private Long kakaoId;
    private MemberStatus status;
}
