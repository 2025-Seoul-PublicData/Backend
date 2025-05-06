package com.example.seoulpublicdata2025backend.domain.story.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "story")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Story {

    @Id
    @Column(name = "story_id", nullable = false)
    private Long storyId;

    @Column(name = "story_title", nullable = false)
    private String storyTitle;

    @Column(name = "story_time", nullable = false)
    private LocalDate storyTime;

    @Column(name = "story_detail", nullable = false, columnDefinition = "TEXT")
    private String storyDetail;

    @Builder.Default
    @Column(name = "story_likes", nullable = false)
    private Long storyLikes = 0L;

    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    @Column(name = "source", length = 255)
    private String source;
}
