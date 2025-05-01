package com.example.seoulpublicdata2025backend.domain.naverReceipt.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.component.NaverOcrClient;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.component.NaverReceiptParser;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyLocationTypeDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.NaverOcrResponseDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoRequestDto;
import com.example.seoulpublicdata2025backend.domain.naverReceipt.dto.ReceiptInfoResponseDto;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundCompanyException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverReceiptServiceImpl implements NaverReceiptService {

    private final CompanyRepository companyRepository;
    private final NaverReceiptParser parser;
    private final NaverOcrClient ocrClient;

    @Override
    public ReceiptInfoResponseDto getCompanyInformation(ReceiptInfoRequestDto dto) {
        CompanyLocationTypeDto companyDto =
                companyRepository.findCompanyLocationTypeByCompanyId(dto.getCompanyId()).orElseThrow(
                        ()-> new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND)
                );
        NaverOcrResponseDto result = ocrClient.callOcrApi(dto.getFile());
        ReceiptInfoDto receiptInfoDto = parser.extractInfoFromOcr(result);
        if(isNotSameCompany(companyDto, receiptInfoDto)) {
            throw new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND);
        }

        /*
        여기서 금액을 추출해서 사용자가 어떤 기업에 얼마를 사용했는지 정보를 추출해야 한다.
         */
        return ReceiptInfoResponseDto.of(receiptInfoDto, companyDto.getLocation());
    }

    private boolean isNotSameCompany(CompanyLocationTypeDto companyDto, ReceiptInfoDto receiptInfoDto) {
        String companyLocation = companyDto.getCompanyLocation();
        String companyAddress = receiptInfoDto.getStoreAddress();
        LevenshteinDistance levenshtein = new LevenshteinDistance(null);

        String normalizedCompanyLocation = normalize(companyLocation);
        String normalizedCompanyAddress = normalize(companyAddress);

        int distance = levenshtein.apply(normalizedCompanyLocation, normalizedCompanyAddress);
        int maxLen = Math.max(normalizedCompanyLocation.length(), normalizedCompanyAddress.length());

        if (maxLen == 0) return false;
        return 1.0 - ((double) distance / maxLen) < 0.8;
    }

    private static String normalize(String input) {
        return input.replaceAll("\\s+", "")       // 모든 공백 제거
                .replaceAll("서울특별시", "서울") // 행정구 정규화
                .replaceAll("[^가-힣0-9]", "")  // 특수 문자 제거
                .toLowerCase()
                .trim();
    }
}
