package com.example.seoulpublicdata2025backend.domain.support.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConsumerSupportProductResponseDetailDto {
    private String bankName;
    private String productName;
    private String productType;
    private String benefit;
    private String productLink;
    private String productDescription;
    private String method;
    private String period;
}
