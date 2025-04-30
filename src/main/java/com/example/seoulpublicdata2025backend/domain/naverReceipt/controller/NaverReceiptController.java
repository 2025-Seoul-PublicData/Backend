package com.example.seoulpublicdata2025backend.domain.naverReceipt.controller;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoRequestDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.service.NaverReceiptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverReceiptController {

    private final NaverReceiptService naverReceiptService;

    @PostMapping("/naver/receipt")
    public ResponseEntity<ReceiptInfoResponseDto> parseNaverReceipt(@Valid @ModelAttribute ReceiptInfoRequestDto dto) {
        ReceiptInfoResponseDto information = naverReceiptService.getCompanyInformation(dto);
        return ResponseEntity.ok(information);
    }
}
