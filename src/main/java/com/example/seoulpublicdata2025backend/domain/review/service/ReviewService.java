package com.example.seoulpublicdata2025backend.domain.review.service;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewService {

    // review CUD
    CompanyReviewDto creatCompanyReview(CompanyReviewDto companyReviewDto);

    CompanyReviewDto updateCompanyReview(Long paymentInfoConfirmNum, LocalDateTime paymentInfoTime, CompanyReviewDto companyReviewDto);

    CompanyReviewDto deleteCompanyReview(Long paymentInfoConfirmNum, LocalDateTime paymentInfoTime);

    List<ReviewDto> getAllCompanyReviews(Long companyId);

    Page<ReviewDto> getPagingCompanyReviews(Long companyId, Pageable pageable);

    Double getTemperature(Long companyId);

    List<MemberReviewDto> getAllMyReviews();

    // 리뷰 개수 Company
    Long getCountCompanyReview(Long companyId);

    // 리뷰 개수 Member
    Long getCountMemberReview();
}
