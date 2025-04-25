package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class InvalidReceiptException extends CustomException {
    public InvalidReceiptException(ErrorCode errorCode) {
        super(errorCode);
    }
}
