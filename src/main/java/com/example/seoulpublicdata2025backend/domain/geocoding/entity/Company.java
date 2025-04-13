package com.example.seoulpublicdata2025backend.domain.geocoding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_location")
    private String companyLocation;

    private double latitude;
    private double longitude;

    private String companyTelNum;
    private String companyBusiness;
    private int companyCategory;
    // 1: 기타, 2: 쇼핑, 3: 복합공간, 4. 생활서비스, 5. 교육
    // 6: IT/디지털, 7: 음식점, 8: 카페, 9: 물류



}
