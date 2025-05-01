package com.example.seoulpublicdata2025backend.global.swagger.annotations.naverReceipt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "OCR 영수증 분석",
        description = "기업 ID와 영수증 이미지를 업로드하여, OCR로 분석된 결제 정보를 반환합니다."
)
@Parameters({
        @Parameter(name = "companyId", description = "기업 ID", required = true, example = "123"),
        @Parameter(name = "file", description = "영수증 이미지 파일", required = true, schema = @Schema(type = "string", format = "binary"))
})
@ApiResponse(
        responseCode = "200",
        description = "OCR 분석 성공",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "OCR 분석 성공 예시",
                        description = "영수증 분석 성공 시 반환되는 매장 정보",
                        value = """
                                {
                                  "storeName": "스타벅스 서울대입구역점",
                                  "storeAddress": "서울특별시 관악구 남부순환로 1817",
                                  "storeTel": "1522-3232",
                                  "orderDateTime": "2025-05-01T15:24:34",
                                  "location": {
                                    "lat": 37.48123,
                                    "lng": 126.9523
                                  }
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "400",
        description = "유효하지 않은 요청 데이터 or OCR 요청 실패",
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(
                                name = "요청 유효성 오류",
                                value = """
                                        {
                                          "status": 400,
                                          "code": "INVALID_INPUT_VALUE",
                                          "message": "입력값이 올바르지 않습니다.",
                                          "errors": [
                                            {
                                              "field": "companyId",
                                              "value": "",
                                              "reason": "Company ID는 필수입니다."
                                            },
                                            {
                                              "field": "file",
                                              "value": "",
                                              "reason": "영수증 사진은 필수입니다."
                                            }
                                          ],
                                          "time": "2025-05-01T21:00:00"
                                        }
                                        """
                        ),
                        @ExampleObject(
                                name = "OCR 요청 오류",
                                value = """
                                        {
                                          "status": 400,
                                          "code": "NAVER_OCR_BAD_REQUEST",
                                          "message": "NAVER OCR 요청이 잘못되었습니다. (400 Bad Request)",
                                          "errors": [],
                                          "time": "2025-05-01T21:00:00"
                                        }
                                        """
                        ),
                        @ExampleObject(
                                name = "OCR 요청 오류",
                                value = """
                                        {
                                          "status": 500,
                                          "code": "NAVER_OCR_INTERNAL_SERVER_ERROR",
                                          "message": "NAVER OCR 서버 오류가 발생했습니다. (500 Internal Server Error)",
                                          "errors": [],
                                          "time": "2025-05-01T21:00:00"
                                        }
                                        """
                        )
                }
        )
)
@ApiResponse(
        responseCode = "404",
        description = "기업을 찾을 수 없음 (ID 없음 또는 주소 불일치)",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "기업 찾기 실패",
                        value = """
                                {
                                  "status": 404,
                                  "code": "COMPANY_NOT_FOUND",
                                  "message": "기업을 찾을 수 없습니다.",
                                  "errors": [],
                                  "time": "2025-05-01T21:00:00"
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "500",
        description = "OCR 서버 내부 오류",
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(
                                name = "서버 내부 오류",
                                value = """
                                        {
                                          "status": 500,
                                          "code": "INTERNAL_SERVER_ERROR",
                                          "message": "서버에 오류가 발생했습니다.",
                                          "errors": [],
                                          "time": "2025-05-01T21:00:00"
                                        }
                                        """
                        ),
                        @ExampleObject(
                                name = "OCR 서버 오류",
                                value = """
                                        {
                                          "status": 500,
                                          "code": "NAVER_OCR_INTERNAL_SERVER_ERROR",
                                          "message": "NAVER OCR 서버 오류가 발생했습니다. (500 Internal Server Error)",
                                          "errors": [],
                                          "time": "2025-05-01T21:00:00"
                                        }
                                        """
                        )
                }
        )
)
public @interface NaverReceiptDocs {
}
