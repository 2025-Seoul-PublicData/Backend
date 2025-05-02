package com.example.seoulpublicdata2025backend.domain.company.entity;

import lombok.Getter;

@Getter
public enum CompanyType {
    PRE("예비"),
    SOCIAL_SERVICE("사회서비스제공형"),
    JOB_PROVISION("일자리제공형"),
    COMPANY_CONTRIBUTION("지역사회공헌형"),
    MIXED("혼합형"),
    ETC("기타(창의ㆍ혁신)형");

    private final String koreanName;

    CompanyType(String koreanName) {
        this.koreanName = koreanName;
    }

    public static CompanyType fromKorean(String koreanName) {
        for (CompanyType type : values()) {
            if (type.koreanName.equals(koreanName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown CompanyType: " + koreanName);
    }
}
