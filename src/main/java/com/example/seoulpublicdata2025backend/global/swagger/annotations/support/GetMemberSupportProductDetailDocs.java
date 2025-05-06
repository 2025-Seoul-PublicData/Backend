package com.example.seoulpublicdata2025backend.global.swagger.annotations.support;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "소비자 지원 상품 조회",
        description = "해당 id를 가진 소비자 지원 상품을 조회합니다.",
        parameters = {
                @Parameter(name = "productId", description = "소비자 지원 상품 ID", required = true)
        },
        responses = {
                @ApiResponse(responseCode = "200", description = "소비자 지원 상품 조회 성공"),
        }
)
public @interface GetMemberSupportProductDetailDocs {
}
