package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import com.example.seoulpublicdata2025backend.domain.story.dto.StoryDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "스토리 상세 조회",
        description = "스토리 ID를 기반으로 상세 정보를 조회합니다."
)
@Parameters({
        @Parameter(name = "storyId", description = "조회할 스토리 ID", example = "1", required = true)
})
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "스토리 상세 조회 성공",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StoryDetailDto.class))),
        @ApiResponse(responseCode = "404", description = "스토리를 찾을 수 없음")
})
public @interface GetStoryDetailDocs {
}
