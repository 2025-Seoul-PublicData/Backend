package com.example.seoulpublicdata2025backend.global.swagger.annotations.review;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "가게 온도 조회",
        description = "특정 회사에 대한 리뷰 온도(평균 점수)를 조회합니다."
)
@Parameters({
        @Parameter(name = "companyId", description = "평균 온도를 조회할 회사의 ID", required = true, example = "1")
})
@ApiResponse(
        responseCode = "200",
        description = "평균 온도 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "object"),
                examples = @ExampleObject(
                        name = "평균 온도 예시",
                        description = "평균 온도가 87.5일 경우의 응답",
                        value = """
                                {
                                  "temperature": 87.5
                                }
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
                        value = """
                                {
                                  "status": 404,
                                  "code": "COMPANY_NOT_FOUND",
                                  "message": "해당 회사를 찾을 수 없습니다.",
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
                        name = "서버 오류 예시",
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
public @interface GetTemperatureDocs {}
