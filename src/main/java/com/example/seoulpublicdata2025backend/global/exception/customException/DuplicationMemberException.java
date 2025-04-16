package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class DuplicationMemberException extends AuthenticationException{
    public DuplicationMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
