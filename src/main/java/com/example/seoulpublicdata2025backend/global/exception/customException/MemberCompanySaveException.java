package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class MemberCompanySaveException extends CustomException {
    public MemberCompanySaveException(ErrorCode errorCode) {
        super(errorCode);
    }
}
