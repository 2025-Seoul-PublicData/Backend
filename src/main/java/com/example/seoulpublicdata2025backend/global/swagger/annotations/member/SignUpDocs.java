package com.example.seoulpublicdata2025backend.global.swagger.annotations.member;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
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
        summary = "회원가입",
        description = "개인정보를 인자로 받아 회원가입합니다."
)
@Parameters({
        @Parameter(name = "kakaoId", description = "kakao service 에서 받아온 id", example = "123456789"),
        @Parameter(name = "name", description = "사용자 이름",example = "홍길동"),
        @Parameter(name = "location", description = "사용자가 사는 동네 주소", example = "서울특별시 강남구"),
        @Parameter(name = "role", description = "소비자라면 CONSUMER, 사장님이면 CORPORATE", example = "CONSUMER"),
        @Parameter(name = "profileImageUrl", description = "사용자 프로필 사진이 저장된 url", example = "https://k.kakaocdn.net/dn/example-profile.png")
})
@RequestBody(
        description = "회원가입 요청 데이터",
        required = true,
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SignupRequestDto.class),
                examples = @ExampleObject(
                        name = "회원가입 요청 예시",
                        description = "성공적인 회원가입 요청 예시",
                        value = """
                {
                  "kakaoId": 123456789,
                  "name": "홍길동",
                  "location": "서울특별시 강남구",
                  "role": "CONSUMER",
                  "profileImageUrl": "https://k.kakaocdn.net/dn/example-profile.png"
                }
                """
                )
        )
)
@ApiResponse(
        responseCode = "200",
        description = "회원가입에 성공, 반환 타입은 추후 결정하여 올리겠습니다."
)
public @interface SignUpDocs {
}
