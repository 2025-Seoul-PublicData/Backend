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
        summary = "기업 찜 등록",
        description = "현재 로그인한 사용자가 기업을 찜합니다. kakaoId는 인증된 사용자의 쿠키/토큰에서 추출됩니다."
)
@Parameters({
        @Parameter(name = "companyId", description = "찜할 대상 기업의 ID", example = "1")
})
@ApiResponse(responseCode = "200", description = "찜 등록 성공")
@ApiResponse(responseCode = "409", description = "이미 찜한 기업인 경우")
@ApiResponse(responseCode = "404", description = "존재하지 않는 회원 또는 기업")
public @interface SaveCompanyDocs {
}


