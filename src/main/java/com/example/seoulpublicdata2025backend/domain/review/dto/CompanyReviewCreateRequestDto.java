package com.example.seoulpublicdata2025backend.domain.review.dto;

import com.example.seoulpublicdata2025backend.domain.review.entity.ReviewCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyReviewCreateRequestDto {
    @NotNull(message = "기업 id는 필수입니다.")
    private Long companyId;

    private Long paymentInfoConfirmNum; // 승인 번호는 null 허용

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime paymentInfoTime;

    @NotNull(message = "리뷰는 꼭 작성해야 합니다.")
    private String review;

    @NotNull(message = "온도를 체크해주세요.")
    private Double temperature;

    @NotNull(message = "리뷰 카테고리는 필수입니다.")
    @Size(min = 1, message = "리뷰 카테고리를 하나 이상 선택해주세요.")
    private Set<ReviewCategory> reviewCategories;
}
