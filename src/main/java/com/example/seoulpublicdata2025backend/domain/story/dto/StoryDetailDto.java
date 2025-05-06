package com.example.seoulpublicdata2025backend.domain.story.dto;

import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StoryDetailDto {

    private Long storyId;
    private String storyTitle;
    private LocalDate storyTime;
    private String storyDetail;
    private Long storyLikes;
    private String imageUrl;
    private String source;

    public static StoryDetailDto fromEntity(Story story) {
        StoryDetailDto dto = new StoryDetailDto();
        dto.storyId = story.getStoryId();
        dto.storyTitle = story.getStoryTitle();
        dto.storyTime = story.getStoryTime();
        dto.storyDetail = story.getStoryDetail();
        dto.storyLikes = story.getStoryLikes();
        dto.imageUrl = story.getImageUrl();
        dto.source = story.getSource();

        return dto;
    }
}
