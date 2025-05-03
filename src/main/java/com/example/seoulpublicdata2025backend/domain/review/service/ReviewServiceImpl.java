package com.example.seoulpublicdata2025backend.domain.review.service;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final CompanyReviewRepository companyReviewRepository;


    @Override
    public CompanyReviewDto creatCompanyReview(CompanyReviewDto dto) {

        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        Long confirmNum = dto.getPaymentInfoConfirmNum();
        if (confirmNum == null) {
            Long maxConfirmNum = companyReviewRepository.findMaxPaymentInfoConfirmNum();
            confirmNum = (maxConfirmNum == null) ? 1L : maxConfirmNum + 1;
        }

        LocalDateTime paymentTime = (dto.getPaymentInfoTime() != null)
                ? dto.getPaymentInfoTime()
                : LocalDateTime.now();

        CompanyReview entity = CompanyReview.builder()
                .paymentInfoConfirmNum(confirmNum)
                .paymentInfoTime(paymentTime)
                .company(Company.builder().companyId(dto.getCompany().getCompanyId()).build()) // 연관관계는 ID만 설정
                .kakao(Member.builder().kakaoId(currentKakaoId).build())
                .review(dto.getReview())
                .temperature(dto.getTemperature())
                .reviewCategories(dto.getReviewCategories())
                .build();

        CompanyReview saved = companyReviewRepository.save(entity);

        return new CompanyReviewDto(
                saved.getReviewId(),
                saved.getPaymentInfoConfirmNum(),
                saved.getPaymentInfoTime(),
                saved.getCompany(),
                saved.getKakao(),
                saved.getReview(),
                saved.getTemperature(),
                saved.getReviewCategories()
        );
    }

    @Override
    public CompanyReviewDto updateCompanyReview(Long reviewId, CompanyReviewDto dto) {
        CompanyReview entity = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        // 필드 업데이트
        entity.updateReview(dto.getReview(), dto.getTemperature(), dto.getReviewCategories());

        CompanyReview updated = companyReviewRepository.save(entity);

        return new CompanyReviewDto(
                updated.getReviewId(),
                updated.getPaymentInfoConfirmNum(),
                updated.getPaymentInfoTime(),
                updated.getCompany(),
                updated.getKakao(),
                updated.getReview(),
                updated.getTemperature(),
                updated.getReviewCategories()
        );
    }

    @Override
    public CompanyReviewDto deleteCompanyReview(Long reviewId) {
        CompanyReview entity = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        companyReviewRepository.delete(entity);

        return new CompanyReviewDto(
                entity.getReviewId(),
                entity.getPaymentInfoConfirmNum(),
                entity.getPaymentInfoTime(),
                entity.getCompany(),
                entity.getKakao(),
                entity.getReview(),
                entity.getTemperature(),
                entity.getReviewCategories()
        );
    }

    @Override
    public List<ReviewDto> getAllCompanyReviews(Long companyId) {
        List<CompanyReview> reviews = companyReviewRepository.findByCompany_CompanyId(companyId);

        return reviews.stream()
                .map(cr -> new ReviewDto(
                        cr.getCompany().getCompanyId(),
                        cr.getKakao().getKakaoId(),
                        cr.getKakao().getName(),
                        cr.getKakao().getProfileColor(),
                        cr.getReview(),
                        cr.getTemperature(),
                        cr.getReviewCategories()
                ))
                .toList();
    }

    @Override
    public Page<ReviewDto> getPagingCompanyReviews(Long companyId, Pageable pageable) {
        return companyReviewRepository.findByCompany_CompanyId(companyId, pageable)
                .map(cr -> new ReviewDto(
                        cr.getCompany().getCompanyId(),
                        cr.getKakao().getKakaoId(),
                        cr.getKakao().getName(),
                        cr.getKakao().getProfileColor(),
                        cr.getReview(),
                        cr.getTemperature(),
                        cr.getReviewCategories()
                ));
    }

    @Override
    public Double getTemperature(Long companyId) {
        List<CompanyReview> reviews = companyReviewRepository.findByCompany_CompanyId(companyId);

        if (reviews.size() == 0) {
            return 0.0;
        }

        double temperature = 0;
        int reviewsSize = reviews.size();

        for (CompanyReview i: reviews) {
            temperature += i.getTemperature();
        }

        return temperature / reviewsSize;
    }

    @Override
    public List<MemberReviewDto> getAllMyReviews() {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        return companyReviewRepository.findReviewDtoByKakaoId(currentKakaoId);
    }

    @Override
    public Long getCountCompanyReview(Long companyId) {
        return companyReviewRepository.getCountByCompanyId(companyId);
    }

    @Override
    public Long getCountMemberReview() {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        return companyReviewRepository.getCountByKakaoId(currentKakaoId);
    }

}
