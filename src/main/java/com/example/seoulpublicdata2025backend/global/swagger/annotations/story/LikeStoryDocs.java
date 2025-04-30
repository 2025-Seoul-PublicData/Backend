package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "스토리 좋아요",
        description = "스토리에 좋아요를 추가합니다."
)
@Parameters({
        @Parameter(name = "storyId", description = "좋아요할 스토리 ID", example = "1", required = true)
})
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 추가 성공"),
        @ApiResponse(responseCode = "400", description = "이미 좋아요한 스토리입니다.")
})
public @interface LikeStoryDocs {
}
