package com.example.seoulpublicdata2025backend.global.swagger.annotations.member;

import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDetailResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "기업 유형별 소비 내역 조회",
        description = "특정 기업 유형(CompanyType)에 해당하는 회원의 소비 내역을 조회합니다."
)
@Parameters({
        @Parameter(name = "companyType", description = "기업 유형 (예: JOB_PROVISION, SOCIAL_SERVICE 등)", required = true, example = "SOCIAL_SERVICE")
})
@ApiResponse(
        responseCode = "200",
        description = "기업 유형별 소비 내역 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MemberConsumptionDetailResponseDto.class),
                examples = @ExampleObject(
                        name = "소비 내역 필터링 성공 예시",
                        value = """
                                {
                                  "name": "test-user",
                                  "reviewCount": 5,
                                  "consumption": {
                                    "companyType": "사회서비스제공형",
                                    "totalPrice": 13000
                                  }
                                }
                                
                                """
                )
        )
)
@ApiResponse(
        responseCode = "400",
        description = "유효하지 않은 companyType 요청",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        value = """
                                {
                                  "status": 400,
                                  "code": "INVALID_TYPE_VALUE",
                                  "message": "요청 타입이 올바르지 않습니다.",
                                  "errors": [],
                                  "time": "2025-05-01T21:00:00"
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "401",
        description = "인증 실패",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        value = """
                                {
                                  "status": 401,
                                  "code": "INVALID_TOKEN",
                                  "message": "유효하지 않은 토큰입니다.",
                                  "errors": [],
                                  "time": "2025-05-01T21:00:00"
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "500",
        description = "서버 내부 오류",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        value = """
                                {
                                  "status": 500,
                                  "code": "INTERNAL_SERVER_ERROR",
                                  "message": "서버에 오류가 발생했습니다.",
                                  "errors": [],
                                  "time": "2025-05-01T21:00:00"
                                }
                                """
                )
        )
)
public @interface GetMeberConsumptionDetailDocs {
}
