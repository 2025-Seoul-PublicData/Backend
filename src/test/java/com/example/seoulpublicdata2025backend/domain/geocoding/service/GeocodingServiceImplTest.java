package com.example.seoulpublicdata2025backend.domain.geocoding.service;

import com.example.seoulpublicdata2025backend.domain.geocoding.dao.GeocodingRepository;
import com.example.seoulpublicdata2025backend.domain.geocoding.dto.MemberLocationDto;
import com.example.seoulpublicdata2025backend.domain.geocoding.entity.Company;
import com.example.seoulpublicdata2025backend.domain.geocoding.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.geocoding.entity.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GeocodingServiceImpl.class)  // 실제 서비스 로직 주입
class GeocodingServiceImplTest {

    @Autowired
    GeocodingService geocodingService;

    @Autowired
    GeocodingRepository geocodingRepository;

    @BeforeEach
    void setUp() {
        // given: 테스트용 Company 엔티티 저장
        Company company = Company.builder()
                .companyName("어시스타앤파트너스")
                .companyLocation("용산구 한강로 3가 GS한강에클라트 오피스동 505호")
                .location(new Location(37.5314276, 126.9554449))
                .companyTelNum("02-713-7811")
                .companyBusiness("복합공간")
                .companyCategory(CompanyCategory.COMPLEX_SPACE)
                .build();

        geocodingRepository.save(company);
    }


    @Test
    void 네이버URLScheme확인() {
        // given
        MemberLocationDto dto = new MemberLocationDto();
        ReflectionTestUtils.setField(dto, "latitude", 37.550149);
        ReflectionTestUtils.setField(dto, "longitude", 126.924656);
        ReflectionTestUtils.setField(dto, "location", "홍익대학교 서울캠퍼스제4공학관");

        String companyName = "어시스타앤파트너스";

        // when
        String url = geocodingService.getNmapSchemeUrl(dto, companyName);

        // join
        System.out.println(url);
        assertTrue(url.contains("nmap://route/walk"));
    }

}