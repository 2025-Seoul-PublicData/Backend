package com.example.seoulpublicdata2025backend.global.swagger.annotations.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "찜한 기업 개수 조회",
        description = "현재 로그인한 사용자가 찜한 기업의 총 개수를 반환합니다."
)
@ApiResponse(responseCode = "200", description = "개수 조회 성공")
public @interface CountSavedCompaniesDocs {
}