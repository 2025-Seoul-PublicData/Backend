package com.example.seoulpublicdata2025backend.global.swagger.annotations.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "기업 찜 해제",
        description = "현재 로그인한 사용자가 찜한 기업을 해제합니다. kakaoId는 인증된 사용자의 쿠키/토큰에서 추출됩니다."
)
@Parameters({
        @Parameter(name = "companyId", description = "찜 해제할 대상 기업의 ID", example = "1")
})
@ApiResponse(responseCode = "200", description = "찜 해제 성공")
@ApiResponse(responseCode = "404", description = "찜한 기록이 없는 경우")

public @interface UnsaveCompanyDocs {
}