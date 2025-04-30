package com.example.seoulpublicdata2025backend.domain.review.service;

import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReview;
import com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReviewId;
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
import java.util.List;

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

    private LocalDateTime reviewTime1;
    private LocalDateTime reviewTime2;
    private LocalDateTime reviewTime3;

    private void authenticateAs(Long kakaoId) {
        SecurityContextHolder.clearContext();

        UserDetails userDetails = User.builder()
                .username(String.valueOf(kakaoId))
                .password("dummy")
                .roles("CONSUMER")
                .build();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @BeforeEach
    void setUp() {

        // ReviewTime
        reviewTime1 = LocalDateTime.of(2025, 4, 22, 10, 0, 0);
        reviewTime2 = LocalDateTime.of(2025, 4, 22, 10, 1, 0);
        reviewTime3 = LocalDateTime.of(2025, 4, 22, 10, 2, 0);

        // Company
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

        // Member
        Member member = Member.builder()
                .kakaoId(1001L)
                .name("홍길동")
                .location("서울")
                .role(Member.Role.CONSUMER) // enum 값 지정 필요
                .build();
        entityManager.persist(member);
        testKakaoId1 = member.getKakaoId();

        Member member2 = Member.builder()
                .kakaoId(1002L)
                .name("홍길동")
                .location("서울")
                .role(Member.Role.CONSUMER) // enum 값 지정 필요
                .build();
        entityManager.persist(member2);
        testKakaoId2 = member2.getKakaoId();


        // CompanyReview
        CompanyReview review1 = CompanyReview.builder()
                .paymentInfoConfirmNum(1L)
                .paymentInfoTime(reviewTime1)
                .company(company)
                .kakao(member)
                .review("좋아요1!")
                .temperature(85.5)
                .reviewCategory(ReviewCategory.CLEAN)
                .build();
        entityManager.persist(review1);

        CompanyReview review2 = CompanyReview.builder()
                .paymentInfoConfirmNum(2L)
                .paymentInfoTime(reviewTime2)
                .company(company)
                .kakao(member)
                .review("좋아요2!")
                .temperature(88.5)
                .reviewCategory(ReviewCategory.CLEAN)
                .build();
        entityManager.persist(review2);

        CompanyReview review3 = CompanyReview.builder()
                .paymentInfoConfirmNum(3L)
                .paymentInfoTime(reviewTime3)
                .company(company)
                .kakao(member2)
                .review("좋아요3!")
                .temperature(88.5)
                .reviewCategory(ReviewCategory.CLEAN)
                .build();
        entityManager.persist(review3);

    }

    @Test
    void getAllCompanyReviews() {
        List<ReviewDto> reviewDtos = reviewService.getAllCompanyReviews(testCompanyId);
        assertEquals(3, reviewDtos.size());
        assertEquals("좋아요2!", reviewDtos.get(1).getReviewContent());
    }

    @Test
    void getTemperature() {
        Double temperatureMean = reviewService.getTemperature(testCompanyId);
        assertEquals(87.5, temperatureMean);
    }

    @Test
    void getAllMyReviews() {
        authenticateAs(testKakaoId1);

        List<ReviewDto> reviewDtos = reviewService.getAllMyReviews();
        assertEquals(2, reviewDtos.size());
        assertEquals("좋아요2!", reviewDtos.get(1).getReviewContent());

        authenticateAs(testKakaoId2);

        List<ReviewDto> reviewDtos2 = reviewService.getAllMyReviews();
        assertEquals(1, reviewDtos2.size());
        assertEquals("좋아요3!", reviewDtos2.get(0).getReviewContent());
    }

    @Test
    void getCountCompanyReview() {
        Long companyReviewCount = reviewService.getCountCompanyReview(testCompanyId);
        assertEquals(3, companyReviewCount);
    }

    @Test
    void getCountMemberReview() {
        authenticateAs(testKakaoId1);
        Long memberReviewCount = reviewService.getCountMemberReview();
        assertEquals(2, memberReviewCount);
    }

    @Test
    void createCompanyReview() {
        LocalDateTime now = LocalDateTime.of(2025, 4, 22, 10, 30);

        CompanyReview review = CompanyReview.builder()
                .paymentInfoConfirmNum(99L)
                .paymentInfoTime(now)
                .company(entityManager.find(Company.class, testCompanyId))
                .kakao(entityManager.find(Member.class, testKakaoId1))
                .review("방금 작성한 리뷰")
                .temperature(95.0)
                .reviewCategory(ReviewCategory.CLEAN)
                .build();

        companyReviewRepository.save(review);

        CompanyReview saved = companyReviewRepository.findById(
                new com.example.seoulpublicdata2025backend.domain.review.entity.CompanyReviewId(99L, now)
        ).orElse(null);

        assertNotNull(saved);
        assertEquals("방금 작성한 리뷰", saved.getReview());
        assertEquals(95.0, saved.getTemperature());
    }

    @Test
    void updateCompanyReview() {
        // given
        CompanyReviewId id = new CompanyReviewId(1L, companyReviewRepository.findById(new CompanyReviewId(1L, reviewTime1)).get().getPaymentInfoTime());
        CompanyReview review = companyReviewRepository.findById(id).orElseThrow();

        review.updateReview("수정된 리뷰", 77.7, ReviewCategory.CLEAN);
        companyReviewRepository.save(review);

        CompanyReview updated = companyReviewRepository.findById(id).orElseThrow();
        assertEquals("수정된 리뷰", updated.getReview());
        assertEquals(77.7, updated.getTemperature());
        assertEquals(ReviewCategory.CLEAN, updated.getReviewCategory());
    }

    @Test
    void deleteCompanyReview() {
        // given
        CompanyReviewId id = new CompanyReviewId(2L, companyReviewRepository.findById(new CompanyReviewId(2L, reviewTime2)).get().getPaymentInfoTime());
        CompanyReview review = companyReviewRepository.findById(id).orElseThrow();

        // when
        companyReviewRepository.delete(review);

        // then
        boolean exists = companyReviewRepository.findById(id).isPresent();
        assertFalse(exists);
    }
}