package com.example.seoulpublicdata2025backend.global.swagger.annotations.aws;

import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
        summary = "S3 Presigned URL 발급",
        description = """
                    S3 버킷에 파일을 직접 업로드할 수 있는 Presigned URL을 발급합니다.
                    요청에는 업로드 파일 종류(type), 원본 파일명이 필요합니다.
                    응답으로 URL과 S3 objectKey가 반환됩니다. accessToken이 포함된 쿠키가 필요합니다.
                    """,
        parameters = {
                @Parameter(name = "type", description = "파일 종류 (현재는 REVIEW, ETC 2가지만 가능합니다. 요청하시면 종류 추가하겠습니다.)", required = true),
                @Parameter(name = "originalFileName", description = "원본 파일명 (예: cat.png)", required = true)
        },
        responses = {
                @ApiResponse(responseCode = "200", description = "Presigned URL 발급 성공",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = PresignedUrlResponseDto.class),
                                examples = @ExampleObject(
                                        name = "SuccessExample",
                                        value = """
                                                    {
                                                      "presignedUrl": "https://s3.bucket.amazonaws.com/...",
                                                      "key": "profiles/user-42/2025/04/24/uuid1234.png"
                                                    }
                                                    """
                                )
                        )
                ),
                @ApiResponse(responseCode = "400", description = "존재하지 않는 사용자 (kakaoId)",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "MEMBER_NOT_FOUND",
                                        value = """
                                                    {
                                                      "status": 400,
                                                      "code": "MEMBER_NOT_FOUND",
                                                      "message": "존재하지 않는 회원입니다.",
                                                      "errors": [],
                                                      "time": "2025-04-24T13:30:00"
                                                    }
                                                    """
                                )
                        )
                ),
                @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "INVALID_TOKEN",
                                        value = """
                                                    {
                                                      "status": 401,
                                                      "code": "INVALID_TOKEN",
                                                      "message": "유효하지 않은 토큰입니다.",
                                                      "errors": [],
                                                      "time": "2025-04-24T13:30:00"
                                                    }
                                                    """
                                )
                        )
                ),
                @ApiResponse(responseCode = "500", description = "서버 내부 에러 발생",
                        content = @Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "INTERNAL_SERVER_ERROR",
                                        value = """
                                                    {
                                                      "status": 500,
                                                      "code": "INTERNAL_SERVER_ERROR",
                                                      "message": "서버에 오류가 발생했습니다.",
                                                      "errors": [],
                                                      "time": "2025-04-24T13:30:00"
                                                    }
                                                    """
                                )
                        )
                )
        }
)
public @interface PresignedUrlDocs {
}
