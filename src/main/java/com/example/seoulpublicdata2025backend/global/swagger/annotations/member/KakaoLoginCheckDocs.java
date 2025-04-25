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
        summary = "카카오 로그인 콜백 처리",
        description = """
        사용자가 카카오 로그인 버튼을 누르면 카카오 서버에서
        카카오 로그인 인가코드를 전달받아 사용자 정보를 조회하고,
        회원가입 여부에 따라 `/`, `/signup`으로 리디렉션합니다.
        
        - 회원이면: 메인 페이지로 이동합니다.
        - 비회원이면: 회원가입 페이지로 이동합니다.
        - 쿠키로 accessToken 전달합니다.
        """
)
@Parameter(name = "code", description = "Kakao Auth Service에서 받은 인가 코드")
@ApiResponse(
        responseCode = "302",
        description = """
            로그인 처리 후 사용자의 상태에 따라 리디렉션됩니다.
            
            - 회원(MEMBER): 메인 페이지 `/`
            - 비회원(PRE_MEMBER): 회원가입 페이지 `/signup`
            
            응답 쿠키:
            - accessToken (accessToken, HttpOnly)
            - kakaoId (로그인한 사용자 ID, HttpOnly)
            """,
        headers = {
                @io.swagger.v3.oas.annotations.headers.Header(
                        name = "Location",
                        description = "리디렉션 될 클라이언트 주소 (ex: http://localhost:5173/ 또는 /signup)"
                ),
                @io.swagger.v3.oas.annotations.headers.Header(
                        name = "Set-Cookie",
                        description = "accessToken, kakaoId 쿠키가 설정됩니다 (HttpOnly)"
                )
        }
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
                                   "errors": [],
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
                                   "errors": [],
                                   "time": "2025-04-15T10:12:34"
                                }
                                """
                )
        )
)
public @interface KakaoLoginCheckDocs {
}
