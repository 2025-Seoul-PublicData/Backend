package com.example.seoulpublicdata2025backend.domain.naverReceipt.service;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.NaverOcrResponseDto;
import java.io.File;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

public class NaverReceiptServiceImpl implements NaverReceiptService {

    @Value("${naver.ocr.secret}")
    private String SECRETKEY;
    private final String TARGETURL = "https://5uaaobajx2.apigw.ntruss.com/custom/v1/41076/24f0c53f0eb29f6ba8f36d54470f7270bb7fc880d67b23d65aa7c4abc8f7bea0/document/receipt";

    @Override
    public void getCompanyInformation(MultipartFile file) {
        NaverOcrResponseDto result = parseNaverReceipt(file);

    }


    private NaverOcrResponseDto parseNaverReceipt(MultipartFile file) {
        String ocrMessage = buildOcrMessage(file);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", new FileSystemResource((File) file));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> messagePart = new HttpEntity<>(ocrMessage, headers);
        parts.add("message", messagePart);


        return RestClient.create().post()
                .uri(TARGETURL)
                .header("X-OCR-SECRET", SECRETKEY)
                .body(parts)
                .retrieve()
                .body(NaverOcrResponseDto.class);
    }

    private String buildOcrMessage(MultipartFile file) {
        String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "receipt";
        String requestId = UUID.randomUUID().toString(); // 고유 ID 생성
        long timestamp = System.currentTimeMillis();     // 현재 시간
        String format = fileName.substring(fileName.lastIndexOf('.') + 1); // 확장자 추출

        return String.format("""
        {
          "version": "V2",
          "requestId": "%s",
          "timestamp": %d,
          "images": [
            {
              "format": "%s",
              "name": "%s"
            }
          ]
        }
        """, requestId, timestamp, format, fileName);
    }

}
