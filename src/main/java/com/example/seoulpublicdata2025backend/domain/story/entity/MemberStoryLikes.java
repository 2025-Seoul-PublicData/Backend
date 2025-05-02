package com.example.seoulpublicdata2025backend.domain.story.entity;

import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_likes")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MemberStoryLikesId.class)
public class MemberStoryLikes {

    @Id
    private Long kakaoId;

    @Id
    private Long storyId;

    @ManyToOne
    @MapsId("kakaoId")
    @JoinColumn(name = "kakao_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne
    @MapsId("storyId")
    @JoinColumn(name = "story_id", insertable = false, updatable = false)
    private Story story;
}
