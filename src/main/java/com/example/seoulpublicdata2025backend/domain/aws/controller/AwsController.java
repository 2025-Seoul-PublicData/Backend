package com.example.seoulpublicdata2025backend.domain.aws.controller;

import com.example.seoulpublicdata2025backend.domain.aws.Service.AwsService;
import com.example.seoulpublicdata2025backend.domain.aws.dto.PresignedUrlRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AwsController {
    private final AwsService awsService;

    @GetMapping("/files/presigned-url/put")
    public String generatePutPresignedUrl(@ModelAttribute PresignedUrlRequestDto dto) {
        return awsService.generatePutPreSignedUrl(dto);
    }
}
