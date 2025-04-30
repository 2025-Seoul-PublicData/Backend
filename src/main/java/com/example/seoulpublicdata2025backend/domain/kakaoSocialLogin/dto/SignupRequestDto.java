package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "위치는 필수입니다.")
    private String location;

    @NotBlank(message = "역할은 필수입니다.")
    @Pattern(regexp = "CONSUMER|CORPORATE", message = "role은 CONSUMER 또는 CORPORATE만 가능합니다.")
    private String role;

    @NotBlank(message = "프로필은 필수입니다")
    private String profileColor;
}
