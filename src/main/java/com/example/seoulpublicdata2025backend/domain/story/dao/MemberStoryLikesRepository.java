package com.example.seoulpublicdata2025backend.domain.story.dao;

import com.example.seoulpublicdata2025backend.domain.story.entity.MemberStoryLikes;
import com.example.seoulpublicdata2025backend.domain.story.entity.MemberStoryLikesId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberStoryLikesRepository extends JpaRepository<MemberStoryLikes, MemberStoryLikesId> {

    List<MemberStoryLikes> findByKakaoId(Long kakaoId);

    Long countByKakaoId(Long kakaoId);

    Long countByStoryId(Long storyId);

    boolean existsByKakaoIdAndStoryId(Long kakaoId, Long storyId);
}
