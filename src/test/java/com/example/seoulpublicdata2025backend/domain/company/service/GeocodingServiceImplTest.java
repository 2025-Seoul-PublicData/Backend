package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dto.MemberLocationDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GeocodingServiceImpl.class)  // 실제 서비스 로직 주입
class GeocodingServiceImplTest {

    @Autowired
    GeocodingService geocodingService;

    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        // given: 테스트용 Company 엔티티 저장
        Company company = Company.builder()
                .companyId(1L)
                .companyName("어시스타앤파트너스")
                .companyLocation("용산구 한강로 3가 GS한강에클라트 오피스동 505호")
                .location(new Location(37.5314276, 126.9554449))
                .companyTelNum("02-713-7811")
                .business("복합공간")
                .companyCategory(CompanyCategory.COMPLEX_SPACE)
                .build();

        companyRepository.save(company);
    }


    @Test
    void 네이버URLScheme확인() {
        // given
        MemberLocationDto dto = new MemberLocationDto();
        ReflectionTestUtils.setField(dto, "latitude", 37.550149);
        ReflectionTestUtils.setField(dto, "longitude", 126.924656);
        ReflectionTestUtils.setField(dto, "location", "홍익대학교 서울캠퍼스제4공학관");

        Long companyId = 1L;

        // when
        String url = geocodingService.getNmapSchemeUrl(dto, companyId);

        // join
        System.out.println(url);
        assertTrue(url.contains("nmap://route/walk"));
    }

}