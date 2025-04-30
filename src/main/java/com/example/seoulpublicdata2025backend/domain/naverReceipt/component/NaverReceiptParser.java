package com.example.seoulpublicdata2025backend.domain.naverReceipt.component;

import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoDto.ItemInfo;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.NaverOcrResponseDto;
import com.example.seoulpublicdata2025backend.global.exception.customException.InvalidReceiptException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NaverReceiptParser {
    // 네이버 OCR 을 거친 데이터로부터 우리가 원하는 데이터를 추출합니다.
    public ReceiptInfoDto extractInfoFromOcr(NaverOcrResponseDto dto) {
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

        ReceiptInfoDto result = new ReceiptInfoDto();
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

        List<ItemInfo> items = new ArrayList<>();

        if (receipt.getSubResults() != null && !receipt.getSubResults().isEmpty()) {
            for (var sub : receipt.getSubResults()) {
                for (var item : sub.getItems()) {
                    if (item.getName() == null || item.getName().getText() == null ||
                            item.getCount() == null || item.getCount().getText() == null ||
                            item.getPrice() == null || item.getPrice().getPrice() == null ||
                            item.getPrice().getPrice().getText() == null) {
                        log.warn("누락된 항목 → name: {}, count: {}, price: {}",
                                item.getName(), item.getCount(), item.getPrice());
                        continue;
                    }

                    ReceiptInfoDto.ItemInfo info = new ReceiptInfoDto.ItemInfo();
                    info.setName(item.getName().getText());
                    info.setCount(item.getCount().getText());
                    info.setPrice(item.getPrice().getPrice().getText());
                    items.add(info);
                }
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
