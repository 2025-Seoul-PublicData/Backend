package com.example.seoulpublicdata2025backend.global.swagger.annotations.member;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
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
        summary = "회원인지 조회",
        description = "인가 코드를 통해 회원인지 아닌지 반환"
)
@Parameter(name = "code", description = "Kakao Auth Service에서 받은 인가 코드")
@ApiResponse(
        responseCode = "200",
        description = "회원/비회원 여부 구분에 성공",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = KakaoAuthResponseDto.class),
                examples = {
                        @ExampleObject(
                                name = "회원 예시",
                                description = "이미 가입된 회원 응답 예시",
                                value = """
                                {
                                    "memberStatus": "회원"
                                }
                                """
                        ),
                        @ExampleObject(
                                name = "비회원 예시",
                                description = "비회원인 경우 응답 예시",
                                value = """
                                        {
                                            "memberStatus": "비회원"
                                        }
                                        """
                        )
                }


        )
)
@ApiResponse(
        responseCode = "400",
        description = "클라이언트의 카카오 요청이 유효하지 않을 경우",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "INVALID_KAKAO_REQUEST",
                        description = "인가 코드가 잘못되었거나 만료된 경우",
                        value = """
                                {
                                   "status": 400,
                                   "code": "INVALID_KAKAO_REQUEST",
                                   "message": "카카오 요청이 유효하지 않습니다.",
                                   "errors": []
                                   "time": "2025-04-15T10:12:34"
                                 }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "500",
        description = "카카오 서버와 통신 중 에러가 발생한 경우",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "KAKAO_SERVER_ERROR",
                        description = "카카오 서버 장애, 응답 오류 등",
                        value = """
                                {
                                   "status": 500,
                                   "code": "KAKAO_SERVER_ERROR",
                                   "message": "카카오 서버 오류입니다.",
                                   "errors": []
                                   "time": "2025-04-15T10:12:34"
                                }
                                """
                )
        )
)
public @interface KakaoLoginCheckDocs {
}
