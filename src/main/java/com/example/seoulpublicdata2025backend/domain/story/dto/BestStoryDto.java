package com.example.seoulpublicdata2025backend.domain.story.dto;

import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import lombok.Getter;

@Getter
public class BestStoryDto {

    private Long storyId;
    private String storyTitle;
    private Long storyLikes;

    public static BestStoryDto fromEntity(Story story) {
        BestStoryDto dto = new BestStoryDto();
        dto.storyId = story.getStoryId();
        dto.storyTitle = story.getStoryTitle();
        dto.storyLikes = story.getStoryLikes();
        return dto;
    }
}
