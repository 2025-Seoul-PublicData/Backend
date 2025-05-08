package com.example.seoulpublicdata2025backend.domain.story.service;

import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.domain.story.dao.MemberStoryLikesRepository;
import com.example.seoulpublicdata2025backend.domain.story.dao.StoryRepository;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import com.example.seoulpublicdata2025backend.domain.story.entity.MemberStoryLikes;
import com.example.seoulpublicdata2025backend.domain.story.entity.MemberStoryLikesId;
import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberStoryLikesServiceImpl implements MemberStoryLikesService {

    private final MemberStoryLikesRepository memberStoryLikesRepository;
    private final MemberRepository memberRepository;
    private final StoryRepository storyRepository;

    @Transactional
    @Override
    public void likeStory(Long storyId) {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        if (memberStoryLikesRepository.existsByKakaoIdAndStoryId(currentKakaoId, storyId)) {
            throw new IllegalStateException("이미 좋아요한 스토리입니다.");
        }

        Member member = memberRepository.findByKakaoId(currentKakaoId)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("스토리가 존재하지 않습니다."));

        story.setStoryLikes(story.getStoryLikes() + 1);

        MemberStoryLikes like = MemberStoryLikes.builder()
                .kakaoId(currentKakaoId)
                .storyId(storyId)
                .member(member)
                .story(story)
                .build();

        memberStoryLikesRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikeStory(Long storyId) {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        MemberStoryLikesId id = new MemberStoryLikesId(currentKakaoId, storyId);

        MemberStoryLikes like = memberStoryLikesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("좋아요 기록이 없습니다."));

        Story story = like.getStory();
        story.setStoryLikes(Math.max(0, story.getStoryLikes() - 1)); // 음수 방지

        memberStoryLikesRepository.delete(like);
    }

    @Override
    public List<StoryPreviewDto> getLikedStories() {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();
        List<MemberStoryLikes> likes = memberStoryLikesRepository.findByKakaoId(currentKakaoId);

        return likes.stream()
                .map(like -> {
                    Story story = like.getStory();
                    return new StoryPreviewDto(
                            story.getStoryId(),
                            story.getStoryTitle(),
                            story.getImageUrl(),
                            story.getStoryLikes());
                })
                .toList();
    }

    @Override
    public Long countLikesByMember() {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        return memberStoryLikesRepository.countByKakaoId(currentKakaoId);
    }
}
