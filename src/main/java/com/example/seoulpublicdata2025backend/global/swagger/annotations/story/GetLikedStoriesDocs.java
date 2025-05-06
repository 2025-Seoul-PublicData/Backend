package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "유저가 좋아요 누른 스토리들 조회",
        description = "로그인한 유저가 좋아요를 누른 모든 스토리 목록을 조회합니다."
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                description = "스토리 조회 성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StoryPreviewDto.class),
                        examples = @ExampleObject(
                                name = "좋아요한 스토리 예시",
                                value = """
                                [
                                  {
                                    "storyId": 1,
                                    "storyTitle": "지구를 위한 작은 실천"
                                  },
                                  {
                                    "storyId": 2,
                                    "storyTitle": "사회적 기업 탐방기"
                                  }
                                ]
                                """
                        )
                )),
        @ApiResponse(responseCode = "500", description = "서버에 오류가 발생했습니다.")
})
public @interface GetLikedStoriesDocs {
}