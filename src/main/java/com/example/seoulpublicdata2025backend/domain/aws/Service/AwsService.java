package com.example.seoulpublicdata2025backend.domain.aws.Service;

import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlRequestDto;

public interface AwsService {
    String generateGetPreSignedUrl(String objectKey);

    String generatePutPreSignedUrl(PresignedUrlRequestDto dto);
}
