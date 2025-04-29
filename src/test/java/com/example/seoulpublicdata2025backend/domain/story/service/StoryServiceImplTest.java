package com.example.seoulpublicdata2025backend.domain.story.service;

import com.example.seoulpublicdata2025backend.domain.story.dao.StoryRepository;
import com.example.seoulpublicdata2025backend.domain.story.dto.BestStoryDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryDetailDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(StoryServiceImpl.class)
class StoryServiceImplTest {

    @Autowired
    private StoryService storyService;

    @Autowired
    private EntityManager entityManager;

    private Story story1;
    private Story story2;
    private Story story3;
    private Story story4;

    @BeforeEach
    void setUp() {
        story1 = Story.builder()
                .storyId(1L)
                .storyTitle("제목1")
                .storyDetail("내용1")
                .storyTime(LocalDate.of(2025, 4, 28))
                .storyLikes(10L)
                .build();
        entityManager.persist(story1);

        story2 = Story.builder()
                .storyId(2L)
                .storyTitle("제목2")
                .storyDetail("내용2")
                .storyTime(LocalDate.of(2025, 4, 27))
                .storyLikes(20L)
                .build();
        entityManager.persist(story2);

        story3 = Story.builder()
                .storyId(3L)
                .storyTitle("제목3")
                .storyDetail("내용3")
                .storyTime(LocalDate.of(2025, 4, 26))
                .storyLikes(5L)
                .build();
        entityManager.persist(story3);

        story4 = Story.builder()
                .storyId(4L)
                .storyTitle("제목4")
                .storyDetail("내용4")
                .storyTime(LocalDate.of(2025, 4, 26))
                .storyLikes(1L)
                .build();
        entityManager.persist(story4);
    }

    @Test
    void 전체_스토리_미리보기_조회() {
        List<StoryPreviewDto> result = storyService.getAllStoryPreview();

        assertEquals(4, result.size());
        assertEquals("제목1", result.get(0).getStoryTitle());
    }

    @Test
    void 스토리_상세_조회() {
        StoryDetailDto result = storyService.storyDetail(story1.getStoryId());

        assertEquals("제목1", result.getStoryTitle());
        assertEquals("내용1", result.getStoryDetail());
    }

    @Test
    void 스토리_상세_조회_실패() {
        assertThrows(RuntimeException.class, () -> storyService.storyDetail(999L));
    }

    @Test
    void 좋아요_상위_3개_조회() {
        List<BestStoryDto> topStories = storyService.bestStory();

        assertEquals(3, topStories.size());
        assertEquals(20L, topStories.get(0).getStoryLikes()); // story2가 제일 좋아요 많음
        assertEquals(10L, topStories.get(1).getStoryLikes()); // story1
        assertEquals(5L, topStories.get(2).getStoryLikes());  // story3
    }
}
