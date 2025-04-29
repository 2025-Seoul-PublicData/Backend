package com.example.seoulpublicdata2025backend.domain.naverReceipt.dto;

import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoDto.ItemInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceiptInfoResponseDto {
    private final String storeName;
    private final String storeAddress;
    private final String storeTel;
    private final String orderDateTime;
    private final List<ItemInfo> items;
    private final Location location;

    public static ReceiptInfoResponseDto of(ReceiptInfoDto dto, Location location) {
        return new ReceiptInfoResponseDto(
                dto.getStoreName(),
                dto.getStoreAddress(),
                dto.getStoreTel(),
                dto.getOrderDateTime(),
                dto.getItems(),
                location
        );
    }
}
