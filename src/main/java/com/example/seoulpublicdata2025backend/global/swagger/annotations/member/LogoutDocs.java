package com.example.seoulpublicdata2025backend.global.swagger.annotations.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "로그아웃", description = "access 토큰을 담은 쿠키를 삭제합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공적으로 로그아웃됨"),
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
})
public @interface LogoutDocs {
}
