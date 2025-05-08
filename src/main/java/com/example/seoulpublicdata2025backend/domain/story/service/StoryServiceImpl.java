package com.example.seoulpublicdata2025backend.domain.story.service;

import com.example.seoulpublicdata2025backend.domain.story.dao.StoryRepository;
import com.example.seoulpublicdata2025backend.domain.story.dto.BestStoryDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryDetailDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService{

    private final StoryRepository storyRepository;

    @Override
    public List<StoryPreviewDto> getAllStoryPreview(Integer size) {
        List<Story> stories;

        if (size == null) {
            // 전체 조회 (storyTime 내림차순 정렬)
            stories = storyRepository.findAll(Sort.by(Sort.Direction.DESC, "storyTime"));
        } else {
            // size만큼만 최신순 조회
            stories = storyRepository
                    .findAll(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "storyTime")))
                    .getContent();
        }

        return stories.stream()
                .map(story -> new StoryPreviewDto(
                        story.getStoryId(),
                        story.getStoryTitle(),
                        story.getImageUrl(),
                        story.getStoryLikes()
                ))
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
