package com.example.seoulpublicdata2025backend.domain.geocoding.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberLocationDto {

    @NotNull(message = "위도는 null이 될 수 없습니다.")
    @DecimalMin(value = "-90.0", inclusive = true, message = "위도는 -90 이상이어야 합니다.")
    @DecimalMax(value = "90.0", inclusive = true, message = "위도는 90 이하이어야 합니다.")
    private Double latitude;

    @NotNull(message = "경도는 null이 될 수 없습니다.")
    @DecimalMin(value = "-180.0", inclusive = true, message = "경도는 -180 이상이어야 합니다.")
    @DecimalMax(value = "180.0", inclusive = true, message = "경도는 180 이하이어야 합니다.")
    private Double longitude;

    @NotNull(message = "주소는 공백일 수 없습니다.")
    private String location;
}
