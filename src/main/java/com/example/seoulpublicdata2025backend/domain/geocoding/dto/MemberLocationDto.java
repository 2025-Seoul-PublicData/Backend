package com.example.seoulpublicdata2025backend.domain.geocoding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberLocationDto {
    
    @NotBlank(message = "주소는 비어 있을 수 없습니다.")
    private double latitude;

    @NotNull(message = "not null")
    private double longitude;

    @NotNull(message = "not null")
    private String location;
}
