package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

import com.example.seoulpublicdata2025backend.domain.story.dto.BestStoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "좋아요 순 상위 3개 스토리 조회",
        description = "좋아요 개수 기준 상위 3개 스토리를 조회합니다."
)
@ApiResponse(
        responseCode = "200",
        description = "Top 3 스토리 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BestStoryDto.class)
        )
)
public @interface BestStoryListDocs {
}
