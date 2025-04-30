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
import org.springframework.stereotype.Service;

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
        return !companyDto.getCompanyLocation().equals(receiptInfoDto.getStoreAddress());
    }
}
