package com.example.seoulpublicdata2025backend.domain.review.controller;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewCreateRequestDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewResponseDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.review.*;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write")
    @CreateReviewDocs
    public CompanyReviewResponseDto createReview(@RequestBody CompanyReviewCreateRequestDto companyReviewCreateRequestDto) {
        return reviewService.creatCompanyReview(companyReviewCreateRequestDto);
    }

    @PostMapping("/update/{reviewId}")
    @UpdateReviewDocs
    public CompanyReviewResponseDto updateReview(@PathVariable Long reviewId, @RequestBody CompanyReviewCreateRequestDto companyReviewCreateRequestDto) {
        return reviewService.updateCompanyReview(reviewId, companyReviewCreateRequestDto);
    }

    @DeleteMapping("/{reviewId}")
    @DeleteReviewDocs
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteCompanyReview(reviewId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/public/get-all-company-reviews")
    @GetCompanyReviewsDocs
    public List<ReviewDto> getAllCompanyReviews(@RequestParam Long companyId) {
        return reviewService.getAllCompanyReviews(companyId);
    }

    @GetMapping("/public/get-company-reviews")
    @GetPagingCompanyReviewsDocs
    public Page<ReviewDto> getPagingCompanyReviews(@RequestParam Long companyId,
                                                   @Positive @RequestParam int size, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "paymentInfoTime"));

        return reviewService.getPagingCompanyReviews(companyId, pageable);
    }

    @GetMapping("/public/get-temperature")
    @GetTemperatureDocs
    public Double getTemperature(@RequestParam Long companyId) {
        return reviewService.getTemperature(companyId);
    }

    @GetMapping("/get-all-member-reviews")
    @GetMemberReviewsDocs
    public List<MemberReviewDto> getAllMemberReviews() {
        return reviewService.getAllMyReviews();
    }
    @GetMapping("/public/get-count-company-review")
    @GetReviewCountDocs
    public Long getCountCompanyReview(@RequestParam Long companyId) {
        return reviewService.getCountCompanyReview(companyId);
    }

    @GetMapping("/get-count-member-review")
    @GetReviewCountDocs
    public Long getCountMemberReview() {
        return reviewService.getCountMemberReview();
    }
}
