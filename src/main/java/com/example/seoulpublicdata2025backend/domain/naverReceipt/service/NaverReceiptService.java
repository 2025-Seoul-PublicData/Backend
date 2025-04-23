package com.example.seoulpublicdata2025backend.domain.naverReceipt.service;

import org.springframework.web.multipart.MultipartFile;

public interface NaverReceiptService {
    void getCompanyInformation(MultipartFile file);
}
