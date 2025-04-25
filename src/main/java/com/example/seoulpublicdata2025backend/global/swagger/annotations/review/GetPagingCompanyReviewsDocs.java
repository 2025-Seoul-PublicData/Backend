package com.example.seoulpublicdata2025backend.global.swagger.annotations.review;

import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "회사 리뷰 페이지네이션 조회",
        description = "특정 회사에 대한 리뷰를 페이지네이션하여 조회합니다. 인증 없이 접근 가능합니다."
)
@Parameters({
        @Parameter(name = "companyId", description = "조회할 회사의 ID", example = "1", required = true),
        @Parameter(name = "size", description = "한 페이지당 리뷰 개수 (1 이상)", example = "3", required = true),
        @Parameter(name = "page", description = "조회할 페이지 번호 (0부터 시작)", example = "0", required = false)
})
@ApiResponse(
        responseCode = "200",
        description = "리뷰 페이지 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                        implementation = ReviewDto.class,
                        type = "object"
                ),
                examples = @ExampleObject(
                        name = "페이징된 리뷰 목록",
                        description = "첫 페이지에 size=2로 조회한 리뷰 목록 예시",
                        value = """
                                {
                                  "content": [
                                    {
                                      "companyId": 1,
                                      "kakaoId": 1001,
                                      "review": "깨끗하고 친절했어요.",
                                      "temperature": 88.0,
                                      "reviewCategory": "CLEAN"
                                    },
                                    {
                                      "companyId": 1,
                                      "kakaoId": 1002,
                                      "review": "재방문 의사 있음.",
                                      "temperature": 90.5,
                                      "reviewCategory": "KIND"
                                    }
                                  ],
                                  "pageable": {
                                    "pageNumber": 0,
                                    "pageSize": 2
                                  },
                                  "totalPages": 5,
                                  "totalElements": 10,
                                  "last": false,
                                  "first": true,
                                  "numberOfElements": 2
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "400",
        description = "잘못된 요청 (예: size가 0 이하)",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        value = """
                                {
                                  "status": 400,
                                  "code": "INVALID_INPUT_VALUE",
                                  "message": "입력값이 올바르지 않습니다.",
                                  "errors": [
                                    {
                                      "field": "size",
                                      "value": "0",
                                      "reason": "1 이상의 값을 입력해주세요."
                                    }
                                  ],
                                  "time": "2025-04-22T10:12:34"
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
                                  "status": 500,
                                  "code": "INTERNAL_SERVER_ERROR",
                                  "message": "서버에 문제가 발생했습니다.",
                                  "errors": [],
                                  "time": "2025-04-22T10:12:34"
                                }
                                """
                )
        )
)
public @interface GetPagingCompanyReviewsDocs {
}
