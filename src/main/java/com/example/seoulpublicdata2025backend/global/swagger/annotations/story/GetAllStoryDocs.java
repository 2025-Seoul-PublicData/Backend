package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "전체 스토리 목록 조회",
        description = "전체 스토리 미리보기를 랜덤으로 조회합니다."
)
@ApiResponse(
        responseCode = "200",
        description = "스토리 목록 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StoryPreviewDto.class)
        )
)
public @interface GetAllStoryDocs {
}
