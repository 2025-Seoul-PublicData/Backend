package com.example.seoulpublicdata2025backend.global.exception.customException;

import com.example.seoulpublicdata2025backend.global.exception.customException.CustomException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;

public class SupportAnnouncementNotFoundException extends CustomException {
    public SupportAnnouncementNotFoundException() {
        super(ErrorCode.SUPPORT_ANNOUNCEMENT_NOT_FOUND);
    }
}
