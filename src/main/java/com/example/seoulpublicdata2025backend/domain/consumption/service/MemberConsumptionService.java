package com.example.seoulpublicdata2025backend.domain.consumption.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyLocationTypeDto;

public interface MemberConsumptionService {
    void saveConsumption(CompanyLocationTypeDto companyDto, Long totalPrice);
}
