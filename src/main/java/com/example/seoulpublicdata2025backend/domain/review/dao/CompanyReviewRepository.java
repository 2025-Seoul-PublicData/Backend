package com.example.seoulpublicdata2025backend.domain.review.dao;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {

    List<CompanyReview> findByCompanyIdOrderByReviewIdDesc(Long companyId);

//    @Query("SELECT new com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto(" +
//            "cr.companyId, cr.kakaoId, cr.review, cr.temperature) " +
//            "FROM CompanyReview cr " +
//            "WHERE cr.kakao.kakaoId = :kakaoId " +
//            "ORDER BY cr.reviewId DESC")
//    List<MemberReviewDto> findReviewDtoByKakaoId(@Param("kakaoId")Long kakaoId);

    List<CompanyReview> findByKakaoIdOrderByReviewIdDesc(Long kakaoId);

    @Query("SELECT COUNT(*) " +
            "FROM CompanyReview cr " +
            "WHERE cr.company.companyId = :companyId")
    Long getCountByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(*) " +
            "FROM CompanyReview cr " +
            "WHERE cr.kakao.kakaoId = :kakaoId")
    Long getCountByKakaoId(@Param("kakaoId") Long kakaoId);

    Page<CompanyReview> findByCompanyId(Long companyId, Pageable pageable);

    @Query("SELECT MAX(r.paymentInfoConfirmNum) FROM CompanyReview r")
    Long findMaxPaymentInfoConfirmNum();

    @Query("SELECT COUNT(*) FROM CompanyReview cr "
            + "WHERE cr.kakao.kakaoId = :kakaoId AND cr.company.companyType = :companyType")
    Long getCountByKakaoIdAndCompanyType(
            @Param("kakaoId") Long kakaoId,
            @Param("companyType") CompanyType companyType
    );
}
