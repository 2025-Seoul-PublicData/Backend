package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class AuthenticationException extends CustomException{
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
