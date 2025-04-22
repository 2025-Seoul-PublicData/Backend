package com.example.seoulpublicdata2025backend.domain.aws.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PresignedUrlResponseDto {
    private final String presignedUrl;
    private final String key;
}
