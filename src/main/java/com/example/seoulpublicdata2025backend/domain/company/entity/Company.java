package com.example.seoulpublicdata2025backend.domain.company.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_location")
    private String companyLocation;

    private String business;

    @Column(name = "company_tel_num")
    private String companyTelNum;

    @Column(name = "company_type")
//    @Enumerated(EnumType.STRING)  // converter가 있어서 필요 없다고 합니다.
    @Convert(converter = CompanyTypeConverter.class)
    private CompanyType companyType;

    @Column(name = "company_category")
//    @Enumerated(EnumType.STRING)
    @Convert(converter = CompanyCategoryConverter.class)
    private CompanyCategory companyCategory;

    @Embedded
    private Location location;

}
