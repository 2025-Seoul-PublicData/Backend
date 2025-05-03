package com.example.seoulpublicdata2025backend.domain.review.dto;

import com.example.seoulpublicdata2025backend.domain.review.entity.ReviewCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
public class CompanyReviewResponseDto {

    private Long reviewId;
    private Long paymentInfoConfirmNum;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime paymentInfoTime;
    private Long companyId;
    private Long kakaoId;
    private String review;
    private Double temperature;
    private Set<ReviewCategory> reviewCategories;
}
