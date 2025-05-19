package com.example.seoulpublicdata2025backend.global.swagger.annotations.member;

import com.example.seoulpublicdata2025backend.domain.member.dto.UpdateMemberResponseDto;
import com.example.seoulpublicdata2025backend.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "회원 프로필 수정",
        description = """
        현재 로그인된 사용자의 프로필 정보를 수정합니다.

        ✅ 요청 필드는 모두 필수입니다.
        - 이름 (`name`)
        - 위치 (`location`)
        - 프로필 색상 (`profileColor`)
        """
)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "프로필 수정 성공", content = @Content(schema = @Schema(implementation = UpdateMemberResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface UpdateMemberDocs {
}


