package com.example.seoulpublicdata2025backend.domain.review.dao;

import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, CompanyReviewId> {

    @Query("SELECT new com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto(" +
            "cr.company.companyId, cr.kakao.kakaoId, cr.review, cr.temperature) " +
            "FROM CompanyReview cr " +
            "WHERE cr.company.companyId = :companyId")
    List<ReviewDto> findReviewDtoByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT new com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto(" +
            "cr.company.companyId, cr.kakao.kakaoId, cr.review, cr.temperature) " +
            "FROM CompanyReview cr " +
            "WHERE cr.kakao.kakaoId = :kakaoId")
    List<ReviewDto> findReviewDtoByKakaoId(@Param("kakaoId")Long kakaoId);

    @Query("SELECT COUNT(*) " +
            "FROM CompanyReview cr " +
            "WHERE cr.company.companyId = :companyId")
    Long getCountByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(*) " +
            "FROM CompanyReview cr " +
            "WHERE cr.kakao.kakaoId = :kakaoId")
    Long getCountByKakaoId(@Param("kakaoId") Long kakaoId);

}
