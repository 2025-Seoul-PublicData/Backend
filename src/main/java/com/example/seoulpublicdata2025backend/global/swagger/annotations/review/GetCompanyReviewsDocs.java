package com.example.seoulpublicdata2025backend.global.swagger.annotations.review;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import com.example.seoulpublicdata2025backend.domain.review.dto.ReviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "회사 리뷰 전체 조회",
        description = "특정 회사에 대한 전체 리뷰를 조회합니다."
)
@Parameters({
        @Parameter(name = "companyId", description = "조회할 회사의 ID", example = "1")
})
@ApiResponse(
        responseCode = "200",
        description = "리뷰 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ReviewDto.class),
                examples = @ExampleObject(
                        name = "리뷰 조회 성공 예시",
                        description = "성공적으로 조회된 리뷰 목록",
                        value = """
                                [
                                  {
                                    \"companyId\": 1,
                                    \"kakaoId\": 1001,
                                    \"reviewContent\": \"깔끔하고 친절해요.\",
                                    \"temperature\": 87.5
                                  },
                                  {
                                    \"companyId\": 1,
                                    \"kakaoId\": 1002,
                                    \"reviewContent\": \"재방문 의사 있어요.\",
                                    \"temperature\": 89.0
                                  }
                                ]
                                """
                )
        )
)
@ApiResponse(
        responseCode = "404",
        description = "해당 ID의 회사를 찾을 수 없음",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "회사 없음 예시",
                        description = "해당 ID를 가진 회사를 찾지 못한 경우",
                        value = """
                                {
                                  \"status\": 404,
                                  \"code\": \"COMPANY_NOT_FOUND\",
                                  \"message\": \"해당 회사를 찾을 수 없습니다.\",
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
                        name = "서버 오류 예시",
                        description = "서버 오류로 인해 리뷰를 조회하지 못한 경우",
                        value = """
                                {
                                  \"status\": 500,
                                  \"code\": \"INTERNAL_SERVER_ERROR\",
                                  \"message\": \"서버에 문제가 발생했습니다. 관리자에게 문의하세요.\",
                                  \"errors\": [],
                                  \"time\": \"2025-04-22T10:12:34\"
                                }
                                """
                )
        )
)
public @interface GetCompanyReviewsDocs {
}
