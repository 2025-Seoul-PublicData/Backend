package com.example.seoulpublicdata2025backend.domain.naverReceipt.controller;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ExtractedReceiptInfoDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.service.NaverReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class NaverReceiptController {

    private final NaverReceiptService naverReceiptService;

    @PostMapping("/naver/receipt")
    public ResponseEntity<ExtractedReceiptInfoDto> parseNaverReceipt(
            @RequestBody Long companyId,
            @RequestBody MultipartFile file
    ) {
        ExtractedReceiptInfoDto information = naverReceiptService.getCompanyInformation(file,companyId);
        return ResponseEntity.ok(information);
    }
}
