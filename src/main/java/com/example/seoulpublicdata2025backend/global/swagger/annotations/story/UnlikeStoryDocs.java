package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "스토리 좋아요 취소",
        description = "스토리의 좋아요를 취소합니다."
)
@Parameters({
        @Parameter(name = "storyId", description = "좋아요 취소할 스토리 ID", example = "1", required = true)
})
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
        @ApiResponse(responseCode = "400", description = "좋아요 기록이 존재하지 않습니다.")
})
public @interface UnlikeStoryDocs {
}
