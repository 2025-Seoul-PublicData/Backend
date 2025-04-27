package com.example.seoulpublicdata2025backend.domain.company.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import lombok.Getter;

@Getter
public class CompanyPreviewDto {

    private Long companyId;
    private String companyName;
    private CompanyCategory companyCategory;

    private Double temperature;
    private Long reviewCount;

    private String companyLocation;
    private String business;
    private CompanyType companyType;

    // 변환 메서드
    public static CompanyPreviewDto fromEntity(Company company, Double temperature, Long reviewCount) {
        CompanyPreviewDto dto = new CompanyPreviewDto();
        dto.companyId = company.getCompanyId();
        dto.companyName = company.getCompanyName();
        dto.companyCategory = company.getCompanyCategory();
        dto.temperature = temperature;
        dto.reviewCount = reviewCount;
        dto.companyLocation = company.getCompanyLocation();
        dto.business = company.getBusiness();
        dto.companyType = company.getCompanyType();
        return dto;
    }
}