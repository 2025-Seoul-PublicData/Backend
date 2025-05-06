package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class NotFoundSupportException extends CustomException{
    public NotFoundSupportException(ErrorCode errorCode) {
        super(errorCode);
    }
}
