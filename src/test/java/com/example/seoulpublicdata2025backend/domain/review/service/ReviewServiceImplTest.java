package com.example.seoulpublicdata2025backend.domain.review.service;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.dto.MemberReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import com.example.seoulpublicdata2025backend.domain.review.entity.ReviewCategory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ReviewServiceImpl.class)
class ReviewServiceImplTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    CompanyReviewRepository companyReviewRepository;

    @Autowired
    EntityManager entityManager;

    private Long testCompanyId;
    private Long testKakaoId1;
    private Long testKakaoId2;

    private void authenticateAs(Long kakaoId) {
        SecurityContextHolder.clearContext();
        UserDetails userDetails = User.builder()
                .username(String.valueOf(kakaoId))
                .password("dummy")
                .roles("CONSUMER")
                .build();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
    }

    @BeforeEach
    void setUp() {
        Company company = Company.builder()
                .companyId(1L)
                .companyName("테스트회사")
                .companyLocation("서울")
                .location(new Location(37.5, 127.0))
                .companyTelNum("02-1234-5678")
                .business("기타")
                .companyCategory(CompanyCategory.ETC)
                .build();
        entityManager.persist(company);
        testCompanyId = company.getCompanyId();

        Member member1 = Member.builder()
                .kakaoId(1001L)
                .name("홍길동")
                .profileColor("Gray")
                .location("서울")
                .role(Member.Role.CONSUMER)
                .build();
        entityManager.persist(member1);
        testKakaoId1 = member1.getKakaoId();

        Member member2 = Member.builder()
                .kakaoId(1002L)
                .name("이순신")
                .profileColor("Orange")
                .location("서울")
                .role(Member.Role.CONSUMER)
                .build();
        entityManager.persist(member2);
        testKakaoId2 = member2.getKakaoId();

        for (int i = 1; i <= 3; i++) {
            CompanyReview review = CompanyReview.builder()
                    .paymentInfoConfirmNum(1000L + i)
                    .paymentInfoTime(LocalDateTime.of(2025, 5, 1, 10, i, 0))
                    .company(company)
                    .kakao(i == 3 ? member2 : member1)
                    .kakaoId(i == 3 ? member2.getKakaoId() : member1.getKakaoId())
                    .review("좋아요" + i + "!")
                    .temperature(85.0 + i)
                    .reviewCategories(new HashSet<>(Set.of(ReviewCategory.CLEAN, ReviewCategory.GOOD_QUALITY)))
                    .build();
            entityManager.persist(review);
        }
    }

    @Test
    void getAllCompanyReviews() {
        List<ReviewDto> reviews = reviewService.getAllCompanyReviews(testCompanyId);
        assertEquals(3, reviews.size());
        assertTrue(reviews.get(0).getReviewCategories().contains(ReviewCategory.CLEAN));
    }

    @Test
    void getTemperature() {
        Double avgTemp = reviewService.getTemperature(testCompanyId);
        assertEquals(87.0, avgTemp);
    }

    @Test
    void getAllMyReviews() {
        authenticateAs(testKakaoId1);
        List<MemberReviewDto> reviews = reviewService.getAllMyReviews();
        assertEquals(2, reviews.size());

        authenticateAs(testKakaoId2);
        List<MemberReviewDto> reviews2 = reviewService.getAllMyReviews();
        assertEquals(1, reviews2.size());
    }

    @Test
    void getCountCompanyReview() {
        Long count = reviewService.getCountCompanyReview(testCompanyId);
        assertEquals(3, count);
    }

    @Test
    void getCountMemberReview() {
        authenticateAs(testKakaoId1);
        Long count = reviewService.getCountMemberReview();
        assertEquals(2, count);
    }

    @Test
    void createCompanyReviewWithMultipleCategories() {
        authenticateAs(testKakaoId1);
        Company company = entityManager.find(Company.class, testCompanyId);
        Member member = entityManager.find(Member.class, testKakaoId1);

        CompanyReview newReview = CompanyReview.builder()
                .paymentInfoConfirmNum(9999L)
                .paymentInfoTime(LocalDateTime.of(2025, 5, 1, 12, 0))
                .company(company)
                .kakao(member)
                .kakaoId(member.getKakaoId())
                .review("여러 카테고리 리뷰")
                .temperature(93.0)
                .reviewCategories(new HashSet<>(Set.of(ReviewCategory.CLEAN, ReviewCategory.HELP_OUR_AREA, ReviewCategory.REASONABLE_PRICE)))
                .build();

        companyReviewRepository.save(newReview);
        entityManager.flush();
        entityManager.clear();

        CompanyReview saved = companyReviewRepository.findAll().stream()
                .filter(r -> r.getReview().contains("여러 카테고리"))
                .findFirst().orElseThrow();

        assertEquals(3, saved.getReviewCategories().size());
        assertTrue(saved.getReviewCategories().contains(ReviewCategory.REASONABLE_PRICE));
    }

    @Test
    void updateCompanyReviewByReviewId() {
        CompanyReview target = companyReviewRepository.findAll().get(0);
        Long reviewId = target.getReviewId();

        target.updateReview("수정된 리뷰입니다.", 99.9, new HashSet<>(Set.of(ReviewCategory.GOOD_SELECT)));
        companyReviewRepository.save(target);

        CompanyReview updated = companyReviewRepository.findById(reviewId).orElseThrow();
        assertEquals("수정된 리뷰입니다.", updated.getReview());
        assertEquals(99.9, updated.getTemperature(), 0.01);
        assertTrue(updated.getReviewCategories().contains(ReviewCategory.GOOD_SELECT));
    }

    @Test
    void deleteCompanyReviewByReviewId() {
        CompanyReview target = companyReviewRepository.findAll().get(1);
        Long reviewId = target.getReviewId();

        companyReviewRepository.deleteById(reviewId);
        assertFalse(companyReviewRepository.findById(reviewId).isPresent());
    }
}
