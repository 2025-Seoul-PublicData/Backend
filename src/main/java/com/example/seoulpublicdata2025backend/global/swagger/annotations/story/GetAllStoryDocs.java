package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "스토리 미리보기 목록 조회",
        description = "size가 주어지면 최신순으로 size개 조회, 없으면 전체 랜덤 조회"
)
@Parameter(name = "size", description = "조회할 스토리 개수 (null이면 전체)", example = "5", required = false)
@ApiResponse(
        responseCode = "200",
        description = "스토리 목록 조회 성공",
        content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = StoryPreviewDto.class)),
                examples = @ExampleObject(value = """
                [
                  {
                    "storyId": 1,
                    "storyTitle": "스포츠안전 플랫폼 선정",
                    "imageUrl": "https://example.com/story1.jpg"
                  },
                  {
                    "storyId": 2,
                    "storyTitle": "청년지갑센터 수상",
                    "imageUrl": "https://example.com/story2.jpg"
                  }
                ]
                """)
        )
)
public @interface GetAllStoryDocs {
}
