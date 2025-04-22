package com.example.seoulpublicdata2025backend.global.swagger.annotations.review;

import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "회원 리뷰 조회",
        description = "카카오 ID를 기반으로 해당 회원이 작성한 리뷰 목록을 조회합니다."
)
@Parameters({
        @Parameter(name = "kakaoId", description = "조회할 회원의 kakao ID", example = "1001")
})
@ApiResponse(
        responseCode = "200",
        description = "회원 리뷰 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ReviewDto.class),
                examples = @ExampleObject(
                        name = "회원 리뷰 조회 예시",
                        description = "해당 kakaoId가 작성한 리뷰들",
                        value = """
                                [
                                  {
                                    \"companyId\": 1,
                                    \"kakaoId\": 1001,
                                    \"reviewContent\": \"좋아요!\",
                                    \"temperature\": 88.0
                                  }
                                ]
                                """
                )
        )
)
@ApiResponse(
        responseCode = "404",
        description = "해당 회원을 찾을 수 없음",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        value = """
                                {
                                  \"status\": 404,
                                  \"code\": \"USER_NOT_FOUND\",
                                  \"message\": \"해당 회원을 찾을 수 없습니다.\",
                                  \"errors\": [],
                                  \"time\": \"2025-04-22T10:12:34\"
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "500",
        description = "서버 내부 에러",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        value = """
                                {
                                  \"status\": 500,
                                  \"code\": \"INTERNAL_SERVER_ERROR\",
                                  \"message\": \"서버에 문제가 발생했습니다.\",
                                  \"errors\": [],
                                  \"time\": \"2025-04-22T10:12:34\"
                                }
                                """
                )
        )
)
public @interface GetMemberReviewsDocs {
}
