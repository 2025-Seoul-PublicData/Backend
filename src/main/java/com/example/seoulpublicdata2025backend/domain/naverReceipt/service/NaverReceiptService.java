package com.example.seoulpublicdata2025backend.domain.naverReceipt.service;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoRequestDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface NaverReceiptService {
    ReceiptInfoResponseDto getCompanyInformation(ReceiptInfoRequestDto dto);
}
