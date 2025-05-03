package com.example.seoulpublicdata2025backend.domain.review.service;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewCreateRequestDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewResponseDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    // review CUD
    CompanyReviewResponseDto creatCompanyReview(CompanyReviewCreateRequestDto companyReviewCreateRequestDto);

    CompanyReviewResponseDto updateCompanyReview(Long reviewId, CompanyReviewCreateRequestDto companyReviewCreateRequestDto);

    CompanyReviewResponseDto deleteCompanyReview(Long reviewId);

    List<ReviewDto> getAllCompanyReviews(Long companyId);

    Page<ReviewDto> getPagingCompanyReviews(Long companyId, Pageable pageable);

    Double getTemperature(Long companyId);

    List<MemberReviewDto> getAllMyReviews();

    // 리뷰 개수 Company
    Long getCountCompanyReview(Long companyId);

    // 리뷰 개수 Member
    Long getCountMemberReview();
}
