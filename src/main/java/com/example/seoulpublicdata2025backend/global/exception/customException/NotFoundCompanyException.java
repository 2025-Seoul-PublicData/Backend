package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class NotFoundCompanyException extends CustomException{
    public NotFoundCompanyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
