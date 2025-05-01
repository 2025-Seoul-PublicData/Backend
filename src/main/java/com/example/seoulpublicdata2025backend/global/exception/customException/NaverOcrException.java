package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class NaverOcrException extends CustomException{
    public NaverOcrException(ErrorCode errorCode) {
        super(errorCode);
    }
}
