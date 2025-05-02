package com.example.seoulpublicdata2025backend.domain.review.controller;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/write")
    @CreateReviewDocs
    public CompanyReviewDto createReview(@RequestBody CompanyReviewDto companyReviewDto) {
        return reviewService.creatCompanyReview(companyReviewDto);
    }

    @PostMapping("/update")
    @UpdateReviewDocs
    public CompanyReviewDto updateReview(@PathVariable Long reviewId, @RequestBody CompanyReviewDto companyReviewDto) {
        return reviewService.updateCompanyReview(reviewId, companyReviewDto);
    }

    @DeleteMapping("/{reviewId}")
    @DeleteReviewDocs
    public CompanyReviewDto deleteReview(@PathVariable Long reviewId) {
        return reviewService.deleteCompanyReview(reviewId);
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
