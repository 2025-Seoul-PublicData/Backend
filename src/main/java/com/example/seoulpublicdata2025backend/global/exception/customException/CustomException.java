package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
