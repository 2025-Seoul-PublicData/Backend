package com.example.seoulpublicdata2025backend.global.exception;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String code;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
