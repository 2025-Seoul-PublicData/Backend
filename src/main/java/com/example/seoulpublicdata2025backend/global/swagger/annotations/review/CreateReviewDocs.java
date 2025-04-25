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
        summary = "리뷰 등록",
        description = "새로운 리뷰를 작성합니다. 작성자(kakaoId)는 쿠키에서 자동 추출됩니다."
)
@Parameters({
        @Parameter(name = "paymentInfoConfirmNum", description = "결제 정보 확인 번호", example = "123"),
        @Parameter(name = "paymentInfoTime", description = "결제 시간 (yyyy/MM/dd HH:mm:ss 형식)", example = "2025/04/22 10:00:00"),
        @Parameter(name = "company", description = "리뷰 대상 회사 객체 (companyId 필수)", example = "{\"companyId\": 1}"),
        @Parameter(name = "review", description = "리뷰 내용", example = "좋은 가게였습니다."),
        @Parameter(name = "temperature", description = "온도 점수 (예: 88.5)", example = "88.5"),
        @Parameter(name = "reviewCategory", description = "리뷰 카테고리 (예: CLEAN, KIND, COST 등)", example = "CLEAN")
})
@RequestBody(
        description = "리뷰 등록 요청 데이터 (작성자 kakaoId는 쿠키에서 자동 추출됨)",
        required = true,
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyReviewDto.class),
                examples = @ExampleObject(
                        name = "리뷰 등록 요청 예시",
                        description = "kakaoId는 쿠키에서 자동 추출됨",
                        value = """
                                {
                                  \"paymentInfoConfirmNum\": 123,
                                  \"paymentInfoTime\": \"2025-04-22T10:00:00\",
                                  \"company\": {
                                    \"companyId\": 1
                                  },
                                  \"review\": \"좋은 가게였습니다.\",
                                  \"temperature\": 88.5,
                                  \"reviewCategory\": \"CLEAN\"
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "200",
        description = "요청 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyReviewDto.class),
                examples = @ExampleObject(
                        name = "리뷰 작성 성공",
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
                                  \"review\": \"좋은 가게였습니다.\",
                                  \"temperature\": 88.5,
                                  \"reviewCategory\": \"CLEAN\"
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "400",
        description = "요청 데이터가 유효하지 않음",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "리뷰 요청 실패 예시",
                        description = "입력 값 중 null 값이 있는 경우",
                        value = """
                                {
                                  \"status\": 400,
                                  \"code\": \"INVALID_INPUT_VALUE\",
                                  \"message\": \"입력값이 올바르지 않습니다.\",
                                  \"errors\": [
                                    {
                                      \"field\": \"review\",
                                      \"value\": \"\",
                                      \"reason\": \"리뷰는 필수입니다.\"
                                    }
                                  ],
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
                        name = "리뷰 요청 실패 - 서버 오류",
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
public @interface CreateReviewDocs {
}
