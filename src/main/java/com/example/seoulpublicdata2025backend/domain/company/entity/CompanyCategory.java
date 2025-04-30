package com.example.seoulpublicdata2025backend.domain.company.entity;

import lombok.Getter;

@Getter
public enum CompanyCategory {
    ETC("기타"),
    SHOPPING("쇼핑"),
    COMPLEX_SPACE("복합공간"),
    LIVING_SERVICE("생활서비스"),
    EDUCATION("교육/지원"),
    IT_DIGITAL("IT/디지털"),
    RESTAURANT("음식점"),
    CAFE("카페"),
    MANUFACTURING_TRANSPORTATION("제조/운송"),
    CULTURE_ART("문화/예술");

    private final String koreanName;

    CompanyCategory(String koreanName) {
        this.koreanName = koreanName;
    }

    public static CompanyCategory fromKorean(String koreanName) {
        for (CompanyCategory category : values()) {
            if (category.koreanName.equals(koreanName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown CompanyCategory: " + koreanName);
    }
}
