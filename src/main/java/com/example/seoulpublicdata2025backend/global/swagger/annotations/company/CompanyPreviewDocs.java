package com.example.seoulpublicdata2025backend.global.swagger.annotations.company;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
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
        summary = "회사 프리뷰 조회",
        description = "companyId를 통해 회사 프리뷰 정보를 조회합니다."
)
@Parameters({
        @Parameter(name = "companyId", description = "조회할 회사 ID", example = "1")
})
@ApiResponse(
        responseCode = "200",
        description = "요청 성공 - 회사 프리뷰 정보 반환",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompanyPreviewDto.class),
                examples = @ExampleObject(
                        name = "회사 프리뷰 조회 성공 예시",
                        value = """
                                {
                                  "companyId": 1,
                                  "companyName": "Test Company",
                                  "companyCategory": "RESTAURANT",
                                  "temperature": 36.5,
                                  "reviewCount": 10,
                                  "companyLocation": "Seoul",
                                  "business": "Restaurant",
                                  "companyType": "PRE"
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "400",
        description = "잘못된 요청 (예: companyId 누락)",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "잘못된 요청 예시",
                        value = """
                                {
                                  "status": 400,
                                  "code": "INVALID_INPUT_VALUE",
                                  "message": "companyId는 필수 입력입니다.",
                                  "errors": [
                                    {
                                      "field": "companyId",
                                      "value": null,
                                      "reason": "필수 입력값입니다."
                                    }
                                  ],
                                  "time": "2025-04-28T03:30:00"
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
                                  "message": "서버에 문제가 발생했습니다. 관리자에게 문의하세요.",
                                  "errors": [],
                                  "time": "2025-04-28T03:30:00"
                                }
                                """
                )
        )
)
public @interface CompanyPreviewDocs {
}
