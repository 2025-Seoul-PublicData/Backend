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
public @interface KakaoLoginCheckDocs {
}
