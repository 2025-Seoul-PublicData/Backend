package com.example.seoulpublicdata2025backend.global.swagger.annotations.support;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "지원 공고 상세 조회",
        description = "공고 ID를 통해 지원 공고의 상세 내용을 조회합니다.",
        parameters = {
                @Parameter(name = "id", description = "지원 공고 ID", required = true)
        },
        responses = {
                @ApiResponse(responseCode = "200", description = "상세 조회 성공"),
                @ApiResponse(responseCode = "404", description = "해당 ID의 공고가 존재하지 않음")
        }
)
public @interface SupportDetailDocs {
}
