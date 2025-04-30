package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
class CompanyServiceImplTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    private CompanyService companyService;

    private ReviewService reviewService;

    private CompanyReviewRepository companyReviewRepository;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        reviewService = Mockito.mock(ReviewService.class);
        companyReviewRepository = null;

        companyService = new CompanyServiceImpl(companyRepository, companyReviewRepository, reviewService);

        testCompany = Company.builder()
                .companyId(1L)
                .companyName("테스트기업")
                .companyLocation("서울특별시 종로구")
                .companyCategory(CompanyCategory.EDUCATION)
                .companyType(CompanyType.PRE)
                .business("교육 서비스")
                .location(new Location(37.5665, 126.9780))
                .build();

        entityManager.persist(testCompany);
    }

    @Test
    void 전체_기업_마커_정보_조회() {
        var result = companyService.getAllCompany();

        assertEquals(1, result.size());
        assertEquals(testCompany.getCompanyId(), result.get(0).getCompanyId());
        assertEquals(testCompany.getCompanyName(), result.get(0).getCompanyName());
    }

    @Test
    void 기업_미리보기_성공() {
        // given
        when(reviewService.getTemperature(1L)).thenReturn(36.5);
        when(reviewService.getCountCompanyReview(1L)).thenReturn(3L);

        // when
        CompanyPreviewDto dto = companyService.companyPreview(1L);

        // then
        assertEquals("테스트기업", dto.getCompanyName());
        assertEquals(36.5, dto.getTemperature());
        assertEquals(3L, dto.getReviewCount());
    }

    @Test
    void 존재하지_않는_기업ID_조회시_예외() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> companyService.companyPreview(999L)
        );

        assertEquals("Not Exist CompanyId: 999", exception.getMessage());
    }
}
