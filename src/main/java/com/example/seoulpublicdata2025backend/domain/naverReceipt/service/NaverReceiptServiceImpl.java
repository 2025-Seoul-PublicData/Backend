package com.example.seoulpublicdata2025backend.domain.naverReceipt.service;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ExtractedReceiptInfoDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ExtractedReceiptInfoDto.ItemInfo;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.NaverOcrResponseDto;
import com.example.seoulpublicdata2025backend.global.exception.customException.InvalidReceiptException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NaverReceiptServiceImpl implements NaverReceiptService {

    @Value("${naver.ocr.secret}")
    private String SECRET_KEY;

    @Value("${naver.ocr.target}")
    private String TARGET_URL;

    @Override
    public ExtractedReceiptInfoDto getCompanyInformation(MultipartFile file) {
        NaverOcrResponseDto result = parseNaverReceipt(file);
        return extractInfoFromOcr(result);
    }

    private NaverOcrResponseDto parseNaverReceipt(MultipartFile file) {
        String ocrMessage = buildOcrMessage(file);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", file.getResource());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> messagePart = new HttpEntity<>(ocrMessage, headers);
        parts.add("message", messagePart);


        return RestClient.create().post()
                .uri(TARGET_URL)
                .header("X-OCR-SECRET", SECRET_KEY)
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

    private ExtractedReceiptInfoDto extractInfoFromOcr(NaverOcrResponseDto dto) {
        if (dto.getImages() == null || dto.getImages().isEmpty()) {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_IMAGE_NOT_FOUND);
        }

        var image = dto.getImages().get(0);
        if (image.getReceipt() == null || image.getReceipt().getResult() == null) {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_RESULT_NOT_FOUND);
        }

        var receipt = image.getReceipt().getResult();
        var storeInfo = receipt.getStoreInfo();
        var paymentInfo = receipt.getPaymentInfo();

        if (storeInfo == null || storeInfo.getName() == null || storeInfo.getName().getText() == null) {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_STORE_NAME_NOT_FOUND);
        }

        if (paymentInfo == null || paymentInfo.getDate() == null || paymentInfo.getTime() == null) {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_DATE_OR_TIME_NOT_FOUND);
        }

        String date = getFormattedDate(paymentInfo.getDate());
        String time = getFormattedTime(paymentInfo.getTime());

        ExtractedReceiptInfoDto result = new ExtractedReceiptInfoDto();
        result.setStoreName(storeInfo.getName().getText());
        result.setOrderDateTime(date + " " + time);

        if (storeInfo.getAddresses() != null && !storeInfo.getAddresses().isEmpty()) {
            result.setStoreAddress(storeInfo.getAddresses().get(0).getText());
        } else {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_ADDRESS_NOT_FOUND);
        }

        if (storeInfo.getTel() != null && !storeInfo.getTel().isEmpty()) {
            result.setStoreTel(storeInfo.getTel().get(0).getText());
        } else {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_TEL_NOT_FOUND);
        }

        if (receipt.getSubResults() == null || receipt.getSubResults().isEmpty()) {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_ITEM_NOT_FOUND);
        }

        List<ItemInfo> items = new ArrayList<>();
        for (var sub : receipt.getSubResults()) {
            for (var item : sub.getItems()) {
                if (item.getName() == null || item.getName().getText() == null ||
                        item.getCount() == null || item.getCount().getText() == null ||
                        item.getPrice() == null || item.getPrice().getPrice() == null ||
                        item.getPrice().getPrice().getText() == null) {
                    throw new InvalidReceiptException(ErrorCode.RECEIPT_ITEM_DETAIL_INCOMPLETE);
                }

                ExtractedReceiptInfoDto.ItemInfo info = new ExtractedReceiptInfoDto.ItemInfo();
                info.setName(item.getName().getText());
                info.setCount(item.getCount().getText());
                info.setPrice(item.getPrice().getPrice().getText());
                items.add(info);
            }
        }

        result.setItems(items);
        return result;
    }

    private String getFormattedDate(NaverOcrResponseDto.ImageResult.ReceiptWrapper.ReceiptResult.Field field) {
        if (field == null || field.getFormatted() == null) {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_DATETIME_FORMAT_INVALID);
        }

        var f = field.getFormatted();
        if (f.getYear() != null && f.getMonth() != null && f.getDay() != null) {
            return String.format("%s-%s-%s", f.getYear(), f.getMonth(), f.getDay());
        }

        if (field.getText() != null) return field.getText();
        throw new InvalidReceiptException(ErrorCode.RECEIPT_DATETIME_FORMAT_INVALID);
    }

    private String getFormattedTime(NaverOcrResponseDto.ImageResult.ReceiptWrapper.ReceiptResult.Field field) {
        if (field == null || field.getFormatted() == null) {
            throw new InvalidReceiptException(ErrorCode.RECEIPT_DATETIME_FORMAT_INVALID);
        }

        var f = field.getFormatted();
        if (f.getHour() != null && f.getMinute() != null && f.getSecond() != null) {
            return String.format("%s:%s:%s", f.getHour(), f.getMinute(), f.getSecond());
        }

        if (field.getText() != null) return field.getText().replace(" ", ""); // e.g., "18:41: 17"
        throw new InvalidReceiptException(ErrorCode.RECEIPT_DATETIME_FORMAT_INVALID);
    }


}
