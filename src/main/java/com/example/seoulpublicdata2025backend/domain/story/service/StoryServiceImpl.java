package com.example.seoulpublicdata2025backend.domain.story.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.story.dao.MemberStoryLikesRepository;
import com.example.seoulpublicdata2025backend.domain.story.dao.StoryRepository;
import com.example.seoulpublicdata2025backend.domain.story.dto.BestStoryDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryDetailDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService{

    private final StoryRepository storyRepository;

    @Override
    public List<StoryPreviewDto> getAllStoryPreview() {
        List<Story> storyList = storyRepository.findAll();
//        Collections.shuffle(storyList);   // 랜덤으로 주기 위한 기능

        return storyList.stream()
                .map(story -> new StoryPreviewDto(story.getStoryId(), story.getStoryTitle()))
                .toList();
    }

    @Override
    public StoryDetailDto storyDetail(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("스토리를 찾을 수 없습니다."));

        return StoryDetailDto.fromEntity(story);
    }

    @Override
    public List<BestStoryDto> bestStory() {
        Pageable topThree = PageRequest.of(0, 3);
        List<Story> top3Stories = storyRepository.findTop3ByOrderByStoryLikesDesc(topThree);

        return top3Stories.stream()
                .map(BestStoryDto::fromEntity)
                .toList();
    }
}
