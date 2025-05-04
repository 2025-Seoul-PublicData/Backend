package com.example.seoulpublicdata2025backend.global.swagger.annotations.member;

import com.example.seoulpublicdata2025backend.domain.member.dto.AuthResponseDto;
import com.example.seoulpublicdata2025backend.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
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
        summary = "로그인 사용자 정보 조회",
        description = "현재 로그인한 사용자의 이름과 프로필 컬러를 반환합니다.",
        responses = {
                @ApiResponse(responseCode = "200", description = "조회 성공",
                        content = @Content(schema = @Schema(implementation = AuthResponseDto.class),
                                examples = @ExampleObject(
                                        name = "소비 내역 필터링 성공 예시",
                                        value = """
                                                [
                                                  {
                                                    "name": "test_user",
                                                    "profileColor": "gray"
                                                  }
                                                ]
                                                """
                                ))),
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
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "존재하지 않는 유저",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                  "status": 404,
                                                  "code": "MEMBER_NOT_FOUND",
                                                  "message": "사용자를 찾을 수 없습니다.",
                                                  "errors": [],
                                                  "time": "2025-05-01T21:00:00"
                                                }
                                                """
                                )
                        )
                ),
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
        }
)
public @interface GetAuthMeDocs {
}
