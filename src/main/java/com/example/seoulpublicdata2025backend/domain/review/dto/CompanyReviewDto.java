package com.example.seoulpublicdata2025backend.domain.review.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.entity.ReviewCategory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@AllArgsConstructor
public class CompanyReviewDto {
    private Long paymentInfoConfirmNum;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime paymentInfoTime;

    private Company company;
    private Member kakao;

    @NotNull(message = "리뷰는 꼭 작성해야 합니다.")
    private String review;
    @NotNull(message = "온도를 체크해주세요.")
    private Double temperature;

    private Set<ReviewCategory> reviewCategories;
}
