package com.example.seoulpublicdata2025backend.domain.story.service;

import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;

import java.util.List;

public interface MemberStoryLikesService {
    void likeStory(Long storyId);

    void unlikeStory(Long storyId);

    List<StoryPreviewDto> getLikedStories();

    Long countLikesByMember();
}
