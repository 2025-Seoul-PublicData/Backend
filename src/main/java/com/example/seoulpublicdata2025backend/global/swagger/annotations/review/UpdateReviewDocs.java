package com.example.seoulpublicdata2025backend.global.swagger.annotations.review;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "리뷰 수정",
        description = "리뷰 ID로 기존 리뷰를 수정합니다. 작성자(kakaoId)는 쿠키에서 자동 추출됩니다."
)
@Parameter(name = "reviewId", description = "수정할 리뷰의 ID", example = "100")
@RequestBody(
        description = "수정할 리뷰 내용 (작성자 kakaoId는 쿠키에서 자동 추출됨)",
        required = true,
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyReviewDto.class),
                examples = @ExampleObject(
                        value = """
                                {
                                  "company": {
                                    "companyId": 1
                                  },
                                  "review": "서비스가 개선되었습니다.",
                                  "temperature": 91.2,
                                  "reviewCategories": ["KIND", "CLEAN"]
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "200",
        description = "리뷰 수정 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyReviewDto.class),
                examples = @ExampleObject(
                        value = """
                                {
                                  "reviewId": 100,
                                  "company": {
                                    "companyId": 1
                                  },
                                  "kakao": {
                                    "kakaoId": 1001
                                  },
                                  "review": "서비스가 개선되었습니다.",
                                  "temperature": 91.2,
                                  "reviewCategories": ["KIND", "CLEAN"]
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "404",
        description = "리뷰를 찾을 수 없음",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        value = """
                                {
                                  "status": 404,
                                  "code": "REVIEW_NOT_FOUND",
                                  "message": "해당 리뷰를 찾을 수 없습니다.",
                                  "errors": [],
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
                                  "message": "서버에 문제가 발생했습니다. 관리자에게 문의하세요.",
                                  "errors": [],
                                  "time": "2025-04-22T10:12:34"
                                }
                                """
                )
        )
)
public @interface UpdateReviewDocs {
}
