package com.example.seoulpublicdata2025backend.domain.naverReceipt.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyLocationTypeDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.component.NaverOcrClient;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.component.NaverReceiptParser;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.NaverOcrResponseDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoRequestDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberConsumptionService;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundCompanyException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverReceiptServiceImpl implements NaverReceiptService {

    private final CompanyRepository companyRepository;
    private final MemberConsumptionService memberConsumptionService;
    private final NaverReceiptParser parser;
    private final NaverOcrClient ocrClient;

    @Override
    public ReceiptInfoResponseDto getCompanyInformation(ReceiptInfoRequestDto dto) {
        CompanyLocationTypeDto companyDto =
                companyRepository.findCompanyLocationTypeByCompanyId(dto.getCompanyId()).orElseThrow(
                        () -> new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND)
                );

        NaverOcrResponseDto ocrResponseDto = ocrClient.callOcrApi(dto.getFile());
        ReceiptInfoDto receiptInfoDto = parser.extractInfoFromOcr(ocrResponseDto);

        if (isNotSameCompany(companyDto, receiptInfoDto)) {
            throw new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND);
        }

        memberConsumptionService.saveConsumption(companyDto, extractTotalPrice(ocrResponseDto));

        return ReceiptInfoResponseDto.of(receiptInfoDto, companyDto.getLocation());
    }

    private boolean isNotSameCompany(CompanyLocationTypeDto companyDto, ReceiptInfoDto receiptInfoDto) {
        return !companyDto.getCompanyLocation().equals(receiptInfoDto.getStoreAddress());
    }

    private Long extractTotalPrice(NaverOcrResponseDto dto) {
        try {
            String value = dto.getImages()
                    .getFirst()
                    .getReceipt()
                    .getResult()
                    .getTotalPrice()
                    .getPrice()
                    .getFormatted()
                    .getValue(); // 예: "12,340원"

            // 숫자만 추출 (숫자가 아닌 문자는 모두 제거)
            String numeric = value.replaceAll("[^\\d]", ""); // → "12340"

            return Long.parseLong(numeric);
        } catch (Exception e) {
            log.error("Naver OCR API Response parsing error: {}", e.getMessage());
            return 0L; // 또는 Optional<Long> 반환 고려
        }
    }
}
