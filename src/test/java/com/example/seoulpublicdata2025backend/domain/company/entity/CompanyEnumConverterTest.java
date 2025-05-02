package com.example.seoulpublicdata2025backend.domain.company;

import com.example.seoulpublicdata2025backend.domain.company.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CompanyEnumConverterTest {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {
        em.createNativeQuery("""
            INSERT INTO company (
                company_id,
                company_name,
                company_location,
                business,
                company_tel_num,
                company_type,
                company_category,
                latitude,
                longitude
            ) VALUES (
                111,
                '테스트 회사',
                '서울시 강남구',
                '서비스업',
                '02-1111-1111',
                '일자리제공형',
                '생활서비스',
                37.12345,
                127.54321
            )
        """).executeUpdate();

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("DB의 한글 문자열이 enum으로 올바르게 변환되는지 테스트")
    void testKoreanDbValueToEnumConversion() {
        // when
        Company company = em.find(Company.class, 111L);

        // then
        assertThat(company.getCompanyType()).isEqualTo(CompanyType.JOB_PROVISION); // "일자리제공형"
        assertThat(company.getCompanyCategory()).isEqualTo(CompanyCategory.LIVING_SERVICE); // "생활서비스"
    }
}
