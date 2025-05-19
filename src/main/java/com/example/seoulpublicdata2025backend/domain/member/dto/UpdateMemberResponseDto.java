package com.example.seoulpublicdata2025backend.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateMemberResponseDto {
    private String name;
    private String location;
    private String profileColor;
}
