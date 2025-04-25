package com.example.seoulpublicdata2025backend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDto {
    private Long companyId;
    private Long kakaoId;
    private String reviewContent;
    private Double temperature;
}
