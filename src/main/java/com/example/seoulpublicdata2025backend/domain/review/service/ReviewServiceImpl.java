package com.example.seoulpublicdata2025backend.domain.review.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewCreateRequestDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewResponseDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewUpdateRequestDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import com.example.seoulpublicdata2025backend.global.exception.customException.CustomException;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundCompanyException;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final CompanyReviewRepository companyReviewRepository;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public CompanyReviewResponseDto creatCompanyReview(CompanyReviewCreateRequestDto dto) {

        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        Long confirmNum = dto.getPaymentInfoConfirmNum();
        if (confirmNum == null) {
            Long maxConfirmNum = companyReviewRepository.findMaxPaymentInfoConfirmNum();
            confirmNum = (maxConfirmNum == null) ? 1L : maxConfirmNum + 1;
        }

        LocalDateTime paymentTime = (dto.getPaymentInfoTime() != null)
                ? dto.getPaymentInfoTime()
                : LocalDateTime.now();

        Member findMember = memberRepository.findByKakaoId(currentKakaoId).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));

        Company findCompany = companyRepository.findById(dto.getCompanyId()).orElseThrow(
                () -> new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND));

        CompanyReview entity = CompanyReview.builder()
                .paymentInfoConfirmNum(confirmNum)
                .paymentInfoTime(paymentTime)
                .company(findCompany)
                .companyId(dto.getCompanyId())
                .kakao(findMember)
                .kakaoId(currentKakaoId)
                .review(dto.getReview())
                .temperature(dto.getTemperature())
                .reviewCategories(dto.getReviewCategories())
                .build();

        try {
            CompanyReview saved = companyReviewRepository.save(entity);

            return new CompanyReviewResponseDto(
                    saved.getReviewId(),
                    saved.getPaymentInfoConfirmNum(),
                    saved.getPaymentInfoTime(),
                    saved.getCompanyId(),
                    saved.getKakaoId(),
                    saved.getReview(),
                    saved.getTemperature(),
                    saved.getReviewCategories()
            );
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.DUPLICATE_COMPANY_REVIEW);
        }
    }

    @Override
    @Transactional
    public CompanyReviewResponseDto updateCompanyReview(Long reviewId, CompanyReviewUpdateRequestDto dto) {
        CompanyReview entity = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        // 필드 업데이트
        entity.updateReview(dto.getReview(), dto.getTemperature(), dto.getReviewCategories());

        CompanyReview updated = companyReviewRepository.save(entity);

        return new CompanyReviewResponseDto(
                updated.getReviewId(),
                updated.getPaymentInfoConfirmNum(),
                updated.getPaymentInfoTime(),
                updated.getCompanyId(),
                updated.getKakaoId(),
                updated.getReview(),
                updated.getTemperature(),
                updated.getReviewCategories()
        );
    }

    @Override
    @Transactional
    public CompanyReviewResponseDto deleteCompanyReview(Long reviewId) {
        CompanyReview entity = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        companyReviewRepository.delete(entity);

        return new CompanyReviewResponseDto(
                entity.getReviewId(),
                entity.getPaymentInfoConfirmNum(),
                entity.getPaymentInfoTime(),
                entity.getCompanyId(),
                entity.getKakaoId(),
                entity.getReview(),
                entity.getTemperature(),
                entity.getReviewCategories()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getAllCompanyReviews(Long companyId) {
        List<CompanyReview> reviews = companyReviewRepository.findByCompanyIdOrderByReviewIdDesc(companyId);

        return reviews.stream()
                .map(cr -> new ReviewDto(
                        cr.getCompanyId(),
                        cr.getKakaoId(),
                        cr.getKakao().getName(),
                        cr.getKakao().getProfileColor(),
                        cr.getReview(),
                        cr.getTemperature(),
                        cr.getReviewCategories()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewDto> getPagingCompanyReviews(Long companyId, Pageable pageable) {
        return companyReviewRepository.findByCompanyId(companyId, pageable)
                .map(cr -> new ReviewDto(
                        cr.getCompanyId(),
                        cr.getKakaoId(),
                        cr.getKakao().getName(),
                        cr.getKakao().getProfileColor(),
                        cr.getReview(),
                        cr.getTemperature(),
                        cr.getReviewCategories()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTemperature(Long companyId) {
        List<CompanyReview> reviews = companyReviewRepository.findByCompanyIdOrderByReviewIdDesc(companyId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double temperature = 0;
        int reviewsSize = reviews.size();

        for (CompanyReview i : reviews) {
            temperature += i.getTemperature();
        }

        return temperature / reviewsSize;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberReviewDto> getAllMyReviews() {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();
        List<CompanyReview> reviews = companyReviewRepository.findByKakaoIdOrderByReviewIdDesc(currentKakaoId);

        return reviews.stream()
                .map(cr -> new MemberReviewDto(
                        cr.getCompanyId(),
                        cr.getKakaoId(),
                        cr.getReview(),
                        cr.getTemperature(),
                        cr.getReviewCategories()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountCompanyReview(Long companyId) {
        return companyReviewRepository.getCountByCompanyId(companyId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountMemberReview() {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        return companyReviewRepository.getCountByKakaoId(currentKakaoId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountMemberReviewByType(MemberConsumptionRequestDto dto) {
        CompanyType companyType = dto.getCompanyType();
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();
        return companyReviewRepository.getCountByKakaoIdAndCompanyType(currentKakaoId, companyType);
    }
}
