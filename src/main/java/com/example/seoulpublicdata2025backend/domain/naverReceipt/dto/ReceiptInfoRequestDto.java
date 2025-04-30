package com.example.seoulpublicdata2025backend.domain.naverReceipt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ReceiptInfoRequestDto {
    @NotNull(message = "Company ID는 필수입니다.")
    private Long companyId;

    @NotNull(message = "영수증 사진은 필수입니다.")
    private MultipartFile file;
}
