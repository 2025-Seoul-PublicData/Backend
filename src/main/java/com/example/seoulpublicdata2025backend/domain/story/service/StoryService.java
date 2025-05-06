package com.example.seoulpublicdata2025backend.domain.story.service;

import com.example.seoulpublicdata2025backend.domain.story.dto.BestStoryDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryDetailDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;

import java.util.List;

public interface StoryService {

    List<StoryPreviewDto> getAllStoryPreview(Integer size);

    StoryDetailDto storyDetail(Long storyId);

    List<BestStoryDto> bestStory();

    // 댓글 기능
}
