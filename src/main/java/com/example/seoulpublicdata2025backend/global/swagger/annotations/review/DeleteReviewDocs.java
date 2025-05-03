
package com.example.seoulpublicdata2025backend.global.swagger.annotations.review;

import com.example.seoulpublicdata2025backend.domain.review.dto.CompanyReviewResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
        summary = "리뷰 삭제",
        description = "리뷰 ID로 리뷰를 삭제합니다. 작성자(kakaoId)는 쿠키에서 자동 추출됩니다."
)
@Parameter(name = "reviewId", description = "삭제할 리뷰의 ID", example = "100")
@ApiResponse(
        responseCode = "204",
        description = "리뷰 삭제 성공"
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
public @interface DeleteReviewDocs {
}
