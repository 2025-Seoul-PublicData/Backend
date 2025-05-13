package com.example.seoulpublicdata2025backend.domain.naverReceipt.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceiptInfoResponseDto {
    private final String storeName;
    private final String storeAddress;
    private final String storeTel;
    private final String orderDateTime;
    private final String confirmNumber;
    private final CompanyCategory companyCategory;
    private final Location location;

    public static ReceiptInfoResponseDto of(ReceiptInfoDto dto, CompanyCategory companyCategory, Location location) {
        return new ReceiptInfoResponseDto(
                dto.getStoreName(),
                dto.getStoreAddress(),
                dto.getStoreTel(),
                dto.getOrderDateTime(),
                dto.getConfirmNumber(),
                companyCategory,
                location
        );
    }
}
