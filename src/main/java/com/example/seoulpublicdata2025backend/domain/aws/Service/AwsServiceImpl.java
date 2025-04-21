package com.example.seoulpublicdata2025backend.domain.aws.Service;

import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlRequestDto;
import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlResponseDto;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

    private final S3Presigner s3Presigner;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public PresignedUrlResponseDto generatePutPreSignedUrl(PresignedUrlRequestDto dto) {
        String objectKey = createObjectKey(dto);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(builder -> builder
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
        );

        return PresignedUrlResponseDto.builder()
                .presignedUrl(presignedRequest.url().toString())
                .key(objectKey)
                .build();
    }

    @Override
    public String generateGetPreSignedUrl(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(builder -> builder
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
        );

        return presignedGetObjectRequest.url().toString();
    }

    private String createObjectKey(PresignedUrlRequestDto dto) {
        String originalFileName = dto.getOriginalFileName();
        String ext = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
        String uuid = UUID.randomUUID().toString();
        LocalDate today = LocalDate.now();

        String prefix = switch (dto.getType()) {
            case REVIEW -> "reviews";
            case ETC -> "etc";
        };

        return String.format("%s/user-%d/%d/%02d/%02d/%s.%s",
                prefix, dto.getKakaoId(),
                today.getYear(), today.getMonthValue(), today.getDayOfMonth(),
                uuid, ext
        );
    }

}
