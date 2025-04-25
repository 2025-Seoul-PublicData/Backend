package com.example.seoulpublicdata2025backend.domain.naverReceipt.service;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ExtractedReceiptInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface NaverReceiptService {
    ExtractedReceiptInfoDto getCompanyInformation(MultipartFile file, Long companyId);
}
