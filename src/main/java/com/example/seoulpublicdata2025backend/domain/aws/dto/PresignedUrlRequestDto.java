package com.example.seoulpublicdata2025backend.domain.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PresignedUrlRequestDto {
    private final String originalFileName;
    private final UploadType type;
}
