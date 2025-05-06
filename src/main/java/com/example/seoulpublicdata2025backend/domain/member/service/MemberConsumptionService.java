package com.example.seoulpublicdata2025backend.domain.member.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyLocationTypeDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionRequestDto;
import java.util.List;

public interface MemberConsumptionService {
    void saveConsumption(CompanyLocationTypeDto companyDto, Long totalPrice);

    List<MemberConsumptionDto> findConsumptionByMember();

    MemberConsumptionDto findConsumptionByMemberAndCompanyType(MemberConsumptionRequestDto dto);
}
