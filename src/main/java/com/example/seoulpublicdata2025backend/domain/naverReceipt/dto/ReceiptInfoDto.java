package com.example.seoulpublicdata2025backend.domain.naverReceipt.dto;

import java.util.List;
import lombok.Data;

@Data
public class ReceiptInfoDto {
    private String storeName;
    private String storeAddress;
    private String storeTel;
    private String orderDateTime;
    private List<ItemInfo> items;

    @Data
    public static class ItemInfo {
        private String name;
        private String count;
        private String price;
    }
}

