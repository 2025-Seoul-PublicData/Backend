package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;

public interface CompanyService {

    CompanyPreviewDto companyPreview(Long companyId);
}
