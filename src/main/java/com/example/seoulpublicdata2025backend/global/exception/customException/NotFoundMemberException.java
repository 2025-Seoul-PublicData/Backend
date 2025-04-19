package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class NotFoundMemberException extends AuthenticationException {
    public NotFoundMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
