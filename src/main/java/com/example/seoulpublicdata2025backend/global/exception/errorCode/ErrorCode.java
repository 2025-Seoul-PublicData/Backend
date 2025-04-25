package com.example.seoulpublicdata2025backend.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 1000번대: 인증 관련
    INVALID_CODE("1000", "유효하지 않은 인가코드입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("1001", "인증되지 않은 요청입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("1002","유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_KAKAO_REQUEST("1098", "카카오 인가 요청이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    KAKAO_SERVER_ERROR("1099", "카카오 서버에 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 2000번대: 입력값 검증 실패
    INVALID_INPUT_VALUE("2000", "입력값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_VALUE("2001", "요청 타입이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("2003", "허용되지 않은 HTTP 메서드입니다", HttpStatus.BAD_REQUEST),

    // 3000번대: 회원 관련
    DUPLICATE_MEMBER("3000", "이미 가입된 사용자입니다.", HttpStatus.CONFLICT),
    MEMBER_NOT_FOUND("3001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 4000번대: 권한 관련
    FORBIDDEN("4000", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 5000번대: 서버 오류
    INTERNAL_SERVER_ERROR("5000", "서버에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_IMAGE_URL("5001", "프로필 이미지 URL이 유효하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
