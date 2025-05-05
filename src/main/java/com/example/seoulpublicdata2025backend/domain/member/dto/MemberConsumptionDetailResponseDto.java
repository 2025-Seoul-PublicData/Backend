package com.example.seoulpublicdata2025backend.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberConsumptionDetailResponseDto {
    private MemberConsumptionDto consumption;
    private String name;
    private Long reviewCount;
}
