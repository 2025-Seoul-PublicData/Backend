package com.example.seoulpublicdata2025backend.domain.naverReceipt.component;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.NaverOcrResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class NaverOcrClient {

    @Value("${naver.ocr.secret}")
    private String secretKey;

    @Value("${naver.ocr.target}")
    private String targetUrl;

    public NaverOcrResponseDto callOcrApi(MultipartFile file) {
        String message = buildOcrMessage(file);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", file.getResource());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        parts.add("message", new HttpEntity<>(message, headers));

        return RestClient.create().post()
                .uri(targetUrl)
                .header("X-OCR-SECRET", secretKey)
                .body(parts)
                .retrieve()
                .body(NaverOcrResponseDto.class);
    }

    // NAVER OCR 에 전송하기 위해 필요한 메세지를 만듭니다.
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
