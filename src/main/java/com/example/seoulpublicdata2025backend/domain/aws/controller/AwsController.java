package com.example.seoulpublicdata2025backend.domain.aws.controller;

import com.example.seoulpublicdata2025backend.domain.aws.Service.AwsService;
import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlRequestDto;
import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AwsController {
    private final AwsService awsService;

    @GetMapping("/files/presigned-url")
    public ResponseEntity<PresignedUrlResponseDto> generatePutPresignedUrl(
            @ModelAttribute PresignedUrlRequestDto dto
    ) {
        return ResponseEntity.ok(awsService.generatePutPreSignedUrl(dto));
    }
}
