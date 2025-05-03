package com.example.seoulpublicdata2025backend.domain.naverReceipt.dto;

import lombok.Data;

@Data
public class ReceiptInfoDto {
    private String storeName;
    private String storeAddress;
    private String storeTel;
    private String orderDateTime;
    private String confirmNumber;
}

