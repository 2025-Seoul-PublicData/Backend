package com.example.seoulpublicdata2025backend.domain.support.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsumerSupportProductResponseDto {
    private Integer id;
    private String bankName;
    private String productName;
    private String productType;
    private String benefit;
    private String productDescription;
    private String defaultCategory;
    private String recommendedCategory;
}
