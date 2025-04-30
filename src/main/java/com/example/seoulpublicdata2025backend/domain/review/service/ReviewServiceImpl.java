package com.example.seoulpublicdata2025backend.domain.review.service;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReviewId;
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

        CompanyReview entity = CompanyReview.builder()
                .paymentInfoConfirmNum(dto.getPaymentInfoConfirmNum())
                .paymentInfoTime(dto.getPaymentInfoTime())
                .company(Company.builder().companyId(dto.getCompany().getCompanyId()).build()) // 연관관계는 ID만 설정
                .kakao(Member.builder().kakaoId(currentKakaoId).build())
                .review(dto.getReview())
                .temperature(dto.getTemperature())
                .reviewCategory(dto.getReviewCategory())
                .build();

        CompanyReview saved = companyReviewRepository.save(entity);

        return new CompanyReviewDto(
                saved.getPaymentInfoConfirmNum(),
                saved.getPaymentInfoTime(),
                saved.getCompany(),
                saved.getKakao(),
                saved.getReview(),
                saved.getTemperature(),
                saved.getReviewCategory()
        );    }

    @Override
    public CompanyReviewDto updateCompanyReview(Long paymentInfoConfirmNum, LocalDateTime paymentInfoTime, CompanyReviewDto dto) {
        CompanyReview entity = companyReviewRepository.findById(
                new CompanyReviewId(paymentInfoConfirmNum, paymentInfoTime)
        ).orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        // 필드 업데이트
        entity.updateReview(dto.getReview(), dto.getTemperature(), dto.getReviewCategory());

        CompanyReview updated = companyReviewRepository.save(entity);

        return new CompanyReviewDto(
                updated.getPaymentInfoConfirmNum(),
                updated.getPaymentInfoTime(),
                updated.getCompany(),
                updated.getKakao(),
                updated.getReview(),
                updated.getTemperature(),
                updated.getReviewCategory()
        );
    }

    @Override
    public CompanyReviewDto deleteCompanyReview(Long paymentInfoConfirmNum, LocalDateTime paymentInfoTime) {
        CompanyReviewId id = new CompanyReviewId(paymentInfoConfirmNum, paymentInfoTime);

        CompanyReview entity = companyReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        companyReviewRepository.delete(entity);

        return new CompanyReviewDto(
                entity.getPaymentInfoConfirmNum(),
                entity.getPaymentInfoTime(),
                entity.getCompany(),
                entity.getKakao(),
                entity.getReview(),
                entity.getTemperature(),
                entity.getReviewCategory()
        );
    }

    @Override
    public List<ReviewDto> getAllCompanyReviews(Long companyId) {
        return companyReviewRepository.findReviewDtoByCompanyId(companyId);
    }

    @Override
    public Page<ReviewDto> getPagingCompanyReviews(Long companyId, Pageable pageable) {
        return companyReviewRepository.findReviewDtoByCompanyId(companyId, pageable);
    }

    @Override
    public Double getTemperature(Long companyId) {
        List<ReviewDto> allCompanyReviewDto = companyReviewRepository.findReviewDtoByCompanyId(companyId);

        if (allCompanyReviewDto.size() == 0) {
            return 0.0;
        }

        double temperature = 0;
        int reviewsSize = allCompanyReviewDto.size();

        for (ReviewDto i: allCompanyReviewDto) {
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
