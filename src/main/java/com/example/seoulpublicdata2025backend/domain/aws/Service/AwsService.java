package com.example.seoulpublicdata2025backend.domain.aws.Service;

import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlRequestDto;
import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlResponseDto;

public interface AwsService {
    String generateGetPreSignedUrl(String objectKey);

    PresignedUrlResponseDto generatePutPreSignedUrl(PresignedUrlRequestDto dto);
}
