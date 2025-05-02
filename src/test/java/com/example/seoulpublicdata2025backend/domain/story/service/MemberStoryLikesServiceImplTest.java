package com.example.seoulpublicdata2025backend.domain.story.service;

import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.domain.story.dao.MemberStoryLikesRepository;
import com.example.seoulpublicdata2025backend.domain.story.dao.StoryRepository;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import com.example.seoulpublicdata2025backend.domain.story.entity.MemberStoryLikes;
import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(MemberStoryLikesServiceImpl.class)
class MemberStoryLikesServiceImplTest {

    @Autowired
    private MemberStoryLikesService memberStoryLikesService;

    @Autowired
    private MemberStoryLikesRepository memberStoryLikesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private EntityManager entityManager;

    private Member testMember;
    private Story testStory1;
    private Story testStory2;

    @BeforeEach
    void setUp() {
        // Member 생성
        testMember = Member.builder()
                .kakaoId(1001L)
                .name("홍길동")
                .location("서울")
                .role(Member.Role.CONSUMER)
                .build();
        entityManager.persist(testMember);

        // Story 2개 생성
        testStory1 = Story.builder()
                .storyId(1L)
                .storyTitle("테스트 스토리1")
                .storyDetail("테스트 상세1")
                .storyTime(LocalDate.now())
                .storyLikes(0L)
                .build();
        entityManager.persist(testStory1);

        testStory2 = Story.builder()
                .storyId(2L)
                .storyTitle("테스트 스토리2")
                .storyDetail("테스트 상세2")
                .storyTime(LocalDate.now())
                .storyLikes(0L)
                .build();
        entityManager.persist(testStory2);

        // Authentication 세팅
        setAuthentication(1001L);
    }

    private void setAuthentication(Long kakaoId) {
        User user = new User(String.valueOf(kakaoId), "", List.of());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void 좋아요_누르기_성공() {
        memberStoryLikesService.likeStory(testStory1.getStoryId());

        List<MemberStoryLikes> likes = memberStoryLikesRepository.findByKakaoId(testMember.getKakaoId());

        assertEquals(1, likes.size());
        assertEquals(testStory1.getStoryId(), likes.get(0).getStory().getStoryId());

        Story story = storyRepository.findById(testStory1.getStoryId()).orElseThrow();
        assertEquals(1L, story.getStoryLikes());
    }

    @Test
    void 중복_좋아요_예외() {
        memberStoryLikesService.likeStory(testStory1.getStoryId());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> memberStoryLikesService.likeStory(testStory1.getStoryId()));
        assertEquals("이미 좋아요한 스토리입니다.", exception.getMessage());
    }

    @Test
    void 좋아요_취소하기_성공() {
        memberStoryLikesService.likeStory(testStory1.getStoryId());

        memberStoryLikesService.unlikeStory(testStory1.getStoryId());

        List<MemberStoryLikes> likes = memberStoryLikesRepository.findByKakaoId(testMember.getKakaoId());
        assertEquals(0, likes.size());

        Story story = storyRepository.findById(testStory1.getStoryId()).orElseThrow();
        assertEquals(0L, story.getStoryLikes());
    }

    @Test
    void 좋아요_취소시_좋아요_없으면_예외() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> memberStoryLikesService.unlikeStory(testStory1.getStoryId()));
        assertEquals("좋아요 기록이 없습니다.", exception.getMessage());
    }

    @Test
    void 내가_좋아요_누른_스토리_목록_조회() {
        memberStoryLikesService.likeStory(testStory1.getStoryId());
        memberStoryLikesService.likeStory(testStory2.getStoryId());

        List<StoryPreviewDto> likedStories = memberStoryLikesService.getLikedStories();
        assertEquals(2, likedStories.size());
    }

    @Test
    void 내가_누른_좋아요_개수_조회() {
        memberStoryLikesService.likeStory(testStory1.getStoryId());
        memberStoryLikesService.likeStory(testStory2.getStoryId());

        Long count = memberStoryLikesService.countLikesByMember();
        assertEquals(2L, count);
    }
}
