package com.example.seoulpublicdata2025backend.domain.member.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MemberConsumptionRequestDto {
    @NotNull(message = "기업 타입을 작성해주세요.")
    private CompanyType companyType;
}
