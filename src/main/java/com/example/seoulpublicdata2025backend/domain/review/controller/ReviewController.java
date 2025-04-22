package com.example.seoulpublicdata2025backend.domain.review.controller;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.review.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    @CreateReviewDocs
    public CompanyReviewDto createReview(@RequestBody CompanyReviewDto companyReviewDto) {
        return reviewService.creatCompanyReview(companyReviewDto);
    }

    @PutMapping("/review")
    @UpdateReviewDocs
    public CompanyReviewDto updateReview(@RequestBody CompanyReviewDto companyReviewDto) {
        Long num = companyReviewDto.getPaymentInfoConfirmNum();
        LocalDateTime time = companyReviewDto.getPaymentInfoTime();

        return reviewService.updateCompanyReview(num, time, companyReviewDto);
    }

    @DeleteMapping("/review")
    @DeleteReviewDocs
    public CompanyReviewDto deleteReview(@RequestBody CompanyReviewDto companyReviewDto) {
        Long num = companyReviewDto.getPaymentInfoConfirmNum();
        LocalDateTime time = companyReviewDto.getPaymentInfoTime();

        return reviewService.deleteCompanyReview(num, time);
    }


    @GetMapping("/get-all-company-reviews")
    @GetCompanyReviewsDocs
    public List<ReviewDto> getAllCompanyReviews(@RequestParam Long companyId) {
        return reviewService.getAllCompanyReviews(companyId);
    }

    @GetMapping("/get-temperature")
    @GetTemperatureDocs
    public Double getTemperature(@RequestParam Long companyId) {
        return reviewService.getTemperature(companyId);
    }

    @GetMapping("/get-all-member-reviews")
    @GetMemberReviewsDocs
    public List<ReviewDto> getAllMemberReviews(@RequestParam Long kakaoId) {
        return reviewService.getAllMyReviews(kakaoId);
    }
    @GetMapping("/get-count-company-review")
    @GetReviewCountDocs
    public Long getCountCompanyReview(@RequestParam Long companyId) {
        return reviewService.getCountCompanyReview(companyId);
    }

    @GetMapping("/get-count-member-review")
    @GetReviewCountDocs
    public Long getCountMemberReview(@RequestParam Long kakaoId) {
        return reviewService.getCountMemberReview(kakaoId);
    }
}
