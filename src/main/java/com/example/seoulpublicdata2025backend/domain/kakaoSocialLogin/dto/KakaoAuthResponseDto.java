package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원/비회원 임을 알려주는 DTO")
public class KakaoAuthResponseDto {

    @Schema(description = "회원이면 '회원', 비회원이면 '비회원'을 반환")
    private final String memberStatus;

    private KakaoAuthResponseDto(MemberStatus memberStatus) {
        this.memberStatus = memberStatus.getValue();
    }

    public static KakaoAuthResponseDto of(MemberStatus memberStatus) {
        return new KakaoAuthResponseDto(memberStatus);
    }
}
