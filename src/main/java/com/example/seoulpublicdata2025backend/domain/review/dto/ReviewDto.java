package com.example.seoulpublicdata2025backend.domain.review.dto;

import com.example.seoulpublicdata2025backend.domain.review.entity.ReviewCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class ReviewDto {
    private Long companyId;
    private Long kakaoId;
    private String name;
    private String profileColor;
    private String reviewContent;
    private Double temperature;
    private Set<ReviewCategory> reviewCategories;
}
