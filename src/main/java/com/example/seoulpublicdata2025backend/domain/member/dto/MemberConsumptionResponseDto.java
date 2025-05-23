package com.example.seoulpublicdata2025backend.domain.member.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberConsumptionResponseDto {
    private String name;
    private Long reviewCount;
    private List<MemberConsumptionDto> consumptions;
}
