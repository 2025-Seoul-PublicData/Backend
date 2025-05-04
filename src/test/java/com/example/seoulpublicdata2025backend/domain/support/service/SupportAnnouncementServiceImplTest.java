package com.example.seoulpublicdata2025backend.domain.support.service;

import com.example.seoulpublicdata2025backend.domain.support.dao.SupportAnnouncementRepository;
import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementDetailDto;
import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementPreviewDto;
import com.example.seoulpublicdata2025backend.domain.support.entity.SupportAnnouncement;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(SupportAnnouncementServiceImpl.class)
class SupportAnnouncementServiceImplTest {

    @Autowired
    SupportAnnouncementService supportAnnouncementService;

    @Autowired
    SupportAnnouncementRepository supportAnnouncementRepository;

    @Autowired
    EntityManager entityManager;

    private final List<Integer> savedIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 3; i++) {
            String endDate = (i == 2) ? "상세 링크 참고" : "2025-06-0" + i;

            SupportAnnouncement sa = new SupportAnnouncement(
                    null,
                    "테스트 제목 " + i,
                    "기관 " + i,
                    "2025-05-0" + i,
                    endDate,
                    "공모형",
                    "이것은 테스트 요약 " + i,
                    "http://example.com/" + i,
                    Timestamp.valueOf(LocalDateTime.now()) // 실시간 now()
            );

            entityManager.persist(sa);
            entityManager.flush();
            savedIds.add(sa.getId());

            try {
                Thread.sleep(10); // 💡 10ms 간격을 두어 created_at이 구분되게
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Test
    void getSupportAnnouncementDetail_withTextEndDate_shouldSucceed() {
        Integer id = savedIds.get(1); // 두 번째 항목 = "상세 링크 참고"
        SupportAnnouncementDetailDto result = supportAnnouncementService.getSupportAnnouncementDetail(id);

        assertEquals("테스트 제목 2", result.getTitle());
        assertEquals("상세 링크 참고", result.getEndDate());
    }

    @Test
    void getSupportAnnouncementDetail_withFormattedEndDate_shouldBeParsableAsDate() {
        Integer id = savedIds.get(0); // 첫 번째 항목 = "2025-06-01"
        SupportAnnouncementDetailDto result = supportAnnouncementService.getSupportAnnouncementDetail(id);

        String endDate = result.getEndDate();
        assertNotNull(endDate);

        try {
            LocalDate.parse(endDate);  // 기본 포맷 ISO_LOCAL_DATE (yyyy-MM-dd)
        } catch (DateTimeParseException e) {
            fail("endDate가 LocalDate로 파싱되지 않습니다: " + endDate);
        }
    }

    @Test
    void getSupportAnnouncementPreview_withSize_shouldBeSorted() {
        List<SupportAnnouncementPreviewDto> result = supportAnnouncementService.getSupportAnnouncementPreview(2);

        assertEquals(2, result.size());
        assertEquals("테스트 제목 3", result.get(0).getTitle()); // 최신순 확인
        assertEquals("테스트 제목 2", result.get(1).getTitle());
    }

    @Test
    void getSupportAnnouncementPreview_withoutSize_shouldReturnAll() {
        List<SupportAnnouncementPreviewDto> result = supportAnnouncementService.getSupportAnnouncementPreview(null);
        assertEquals(3, result.size());
    }
}
