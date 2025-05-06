package com.example.seoulpublicdata2025backend.domain.story.controller;

import com.example.seoulpublicdata2025backend.domain.story.dto.BestStoryDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryDetailDto;
import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import com.example.seoulpublicdata2025backend.domain.story.service.MemberStoryLikesService;
import com.example.seoulpublicdata2025backend.domain.story.service.StoryService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.story.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/story")
public class StoryController {

    private final StoryService storyService;
    private final MemberStoryLikesService memberStoryLikesService;

    @GetMapping("/public/all")
    @GetAllStoryDocs
    public List<StoryPreviewDto> getAllStory() {
        return storyService.getAllStoryPreview();
    }

    @GetMapping("/public/detail/{storyId}")
    @GetStoryDetailDocs
    public StoryDetailDto storyDetail(@PathVariable Long storyId){
        return storyService.storyDetail(storyId);
    }

    @PatchMapping("/likes/{storyId}")
    @LikeStoryDocs
    public ResponseEntity<Void> likes(@PathVariable Long storyId) {
        memberStoryLikesService.likeStory(storyId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/dislikes/{storyId}")
    @UnlikeStoryDocs
    public ResponseEntity<Void> dislikes(@PathVariable Long storyId) {
        memberStoryLikesService.unlikeStory(storyId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/best")
    @BestStoryListDocs
    public List<BestStoryDto> bestStoryList() {
        return storyService.bestStory();
    }

    @GetMapping("/member-likes")
    @GetLikedStoriesDocs
    public List<StoryPreviewDto> getLikedStories() {
        return memberStoryLikesService.getLikedStories();
    }

    @GetMapping("/count-member-likes")
    @CountMemberLikesDocs
    public Long countMemberLikes() {
        return memberStoryLikesService.countLikesByMember();
    }

}
