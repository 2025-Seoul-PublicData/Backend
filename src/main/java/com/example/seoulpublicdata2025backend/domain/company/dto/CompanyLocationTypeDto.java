package com.example.seoulpublicdata2025backend.domain.company.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyLocationTypeDto {
    private Long companyId;
    private String companyLocation;
    private CompanyType companyType;
    private Location location;
}
