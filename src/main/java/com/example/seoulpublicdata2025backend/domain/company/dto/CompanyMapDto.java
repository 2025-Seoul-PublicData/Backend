package com.example.seoulpublicdata2025backend.domain.company.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyMapDto {
    private Long companyId;
    private String companyName;
    private Double latitude;
    private Double longitude;
    private CompanyCategory companyCategory;
}
