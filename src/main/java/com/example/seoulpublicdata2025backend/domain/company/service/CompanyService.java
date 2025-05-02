package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyMapDto;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;

import java.util.List;

public interface CompanyService {

    List<CompanyMapDto> getAllCompany();

    CompanyPreviewDto companyPreview(Long companyId);
}
