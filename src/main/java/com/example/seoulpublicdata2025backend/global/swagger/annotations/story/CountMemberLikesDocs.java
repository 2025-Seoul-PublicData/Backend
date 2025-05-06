package com.example.seoulpublicdata2025backend.global.swagger.annotations.story;

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
        summary = "유저가 좋아요한 스토리 개수 조회",
        description = "현재 로그인한 유저가 좋아요를 누른 스토리의 총 개수를 조회합니다."
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                description = "좋아요 개수 조회 성공",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(type = "integer", example = "5"),
                        examples = @ExampleObject(
                                name = "좋아요 개수 예시",
                                value = "3"
                        )
                )),
        @ApiResponse(responseCode = "500", description = "서버에 오류가 발생했습니다.")
})
public @interface CountMemberLikesDocs {
}