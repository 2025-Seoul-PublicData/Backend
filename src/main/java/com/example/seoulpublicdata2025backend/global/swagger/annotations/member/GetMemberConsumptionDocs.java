package com.example.seoulpublicdata2025backend.global.swagger.annotations.member;

import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "회원 소비 내역 조회",
        description = "카카오 ID를 기반으로 해당 사용자의 소비 내역(기업 유형, 총 소비 금액)을 조회합니다."
)
@ApiResponse(
        responseCode = "200",
        description = "소비 내역 조회 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MemberConsumptionResponseDto.class),
                examples = @ExampleObject(
                        name = "소비 내역 예시",
                        value = """
                                {
                                   "name": "test-user",
                                   "reviewCount": 5,
                                   "consumptions": [
                                     {
                                       "companyType": "사회서비스제공형",
                                       "totalPrice": 13000
                                     },
                                     {
                                       "companyType": "일자리제공형",
                                       "totalPrice": 8200
                                     }
                                   ]
                                 }
                                
                                """
                )
        )
)
@ApiResponse(
        responseCode = "401",
        description = "인증 실패 - 토큰이 없거나 만료된 경우",
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
public @interface GetMemberConsumptionDocs {
}
