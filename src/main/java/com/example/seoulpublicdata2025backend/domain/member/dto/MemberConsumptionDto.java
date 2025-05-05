package com.example.seoulpublicdata2025backend.domain.member.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberConsumptionDto {
    private CompanyType companyType;
    private Long totalPrice;
}
