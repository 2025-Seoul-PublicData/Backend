package com.example.seoulpublicdata2025backend.domain.story.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class MemberStoryLikesId implements Serializable {

    @EqualsAndHashCode.Include
    private Long kakaoId;

    @EqualsAndHashCode.Include
    private Long storyId;
}
