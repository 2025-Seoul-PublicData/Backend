package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;

import java.util.List;

public interface MemberCompanySaveService {
    void saveCompany(Long companyId);

    void unSaveCompany(Long companyId);

    List<CompanyPreviewDto> getSavedCompanies();

    Long countSavedByMember();
}
