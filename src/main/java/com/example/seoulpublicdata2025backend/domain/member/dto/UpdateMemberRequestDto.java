package com.example.seoulpublicdata2025backend.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberRequestDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "위치는 필수입니다.")
    private String location;

    @NotBlank(message = "프로필은 필수입니다")
    private String profileColor;
}
