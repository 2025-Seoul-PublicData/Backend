package com.example.seoulpublicdata2025backend.global.swagger.annotations.company;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyMapDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Operation(
        summary = "전체 기업 마커 정보 조회",
        description = "모든 기업의 ID, 이름, 위도, 경도, 카테고리 정보를 반환합니다. 지도 마커용 API입니다."
)
@ApiResponse(
        responseCode = "200",
        description = "조회 성공",
        content = @Content(
                mediaType = "application/json",
                array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                        schema = @Schema(implementation = CompanyMapDto.class)
                ),
                examples = @ExampleObject(
                        name = "전체 기업 마커 정보 예시",
                        description = "기업 ID, 이름, 좌표, 카테고리 반환",
                        value = """
                                [
                                  {
                                    "companyId": 1,
                                    "companyName": "행복나눔협동조합",
                                    "latitude": 37.5665,
                                    "longitude": 126.9780,
                                    "companyCategory": "LIFE"
                                  },
                                  {
                                    "companyId": 2,
                                    "companyName": "소셜벤처 서울",
                                    "latitude": 37.5795,
                                    "longitude": 126.9893,
                                    "companyCategory": "EDUCATION"
                                  }
                                ]
                                """
                )
        )
)
public @interface GetAllCompaniesDocs {
}
