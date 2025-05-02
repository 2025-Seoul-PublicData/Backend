package com.example.seoulpublicdata2025backend.domain.story.dao;

import com.example.seoulpublicdata2025backend.domain.story.entity.Story;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query("SELECT s FROM Story s ORDER BY s.storyLikes DESC")
    List<Story> findTop3ByOrderByStoryLikesDesc(Pageable pageable);
}
