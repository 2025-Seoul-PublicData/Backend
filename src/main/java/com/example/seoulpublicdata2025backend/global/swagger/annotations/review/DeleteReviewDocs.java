
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
        summary = "리뷰 삭제",
        description = "기존 리뷰를 삭제합니다. paymentInfoConfirmNum 및 paymentInfoTime을 기준으로 식별합니다."
)
@Parameters({
        @Parameter(name = "paymentInfoConfirmNum", description = "결제 정보 확인 번호", example = "123"),
        @Parameter(name = "paymentInfoTime", description = "결제 시간 (yyyy/MM/dd HH:mm:ss 형식)", example = "2025/04/22 10:00:00"),
        @Parameter(name = "company", description = "리뷰 대상 회사 객체 (companyId 필수)", example = "{\"companyId\": 1}"),
        @Parameter(name = "kakao", description = "작성자 정보 객체 (kakaoId 필수)", example = "{\"kakaoId\": 1001}")
})
@RequestBody(
        description = "리뷰 삭제 요청 데이터",
        required = true,
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyReviewDto.class),
                examples = @ExampleObject(
                        name = "리뷰 삭제 요청 예시",
                        description = "성공적인 리뷰 삭제 요청 예시",
                        value = """
                                {
                                  \"paymentInfoConfirmNum\": 123,
                                  \"paymentInfoTime\": \"2025-04-22T10:00:00\",
                                  \"company\": {
                                    \"companyId\": 1
                                  },
                                  \"kakao\": {
                                    \"kakaoId\": 1001
                                  }
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "200",
        description = "리뷰 삭제 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyReviewDto.class),
                examples = @ExampleObject(
                        name = "리뷰 삭제 성공 예시",
                        description = "요청이 성공적으로 처리되었을 때",
                        value = """
                                {
                                  \"paymentInfoConfirmNum\": 123,
                                  \"paymentInfoTime\": \"2025-04-22T10:00:00\",
                                  \"company\": {
                                    \"companyId\": 1
                                  },
                                  \"kakao\": {
                                    \"kakaoId\": 1001
                                  },
                                  \"review\": \"서비스가 개선되었습니다.\",
                                  \"temperature\": 91.2,
                                  \"reviewCategory\": \"KIND\"
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
                        name = "리뷰 삭제 실패 - 존재하지 않는 리뷰",
                        description = "리뷰가 존재하지 않아 삭제할 수 없는 경우",
                        value = """
                                {
                                  \"status\": 404,
                                  \"code\": \"REVIEW_NOT_FOUND\",
                                  \"message\": \"해당 리뷰를 찾을 수 없습니다.\",
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
                        name = "리뷰 삭제 실패 - 서버 오류",
                        description = "서버 오류로 인해 요청 처리에 실패한 경우",
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
public @interface DeleteReviewDocs {
}