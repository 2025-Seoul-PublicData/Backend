package com.example.seoulpublicdata2025backend.global.swagger.annotations.support;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "지원 공고 프리뷰 조회",
        description = "최신순으로 지원 공고 프리뷰를 조회합니다. size를 지정하지 않으면 전체를 조회합니다.",
        responses = {
                @ApiResponse(responseCode = "200", description = "프리뷰 조회 성공")
        }
)
public @interface SupportPreviewDocs {
}
