package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    @NotNull(message = "kakaoId는 필수입니다.")
    private Long kakaoId;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "위치는 필수입니다.")
    private String location;

    @NotBlank(message = "역할은 필수입니다.")
    @Pattern(regexp = "CONSUMER|CORPORATE", message = "role은 CONSUMER 또는 CORPORATE만 가능합니다.")
    private String role;

    // TODO : 카카오 프로필은 사용하지 않음. 기본 프로필 중에 어떤 것을 선택했는지를 담으면 될 거 같음.
    private String profileImageUrl;
}
