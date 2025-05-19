package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dao.MemberCompanySaveRepository;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyMapDto;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundCompanyException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyReviewRepository companyReviewRepository;
    private final ReviewService reviewService;

    @Override
    public List<CompanyMapDto> getAllCompany() {
        return companyRepository.findAllCompanyMapData();
    }

    @Override
    public CompanyPreviewDto companyPreview(Long companyId) {
        Company company = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND));

        Double temperatureAvg = reviewService.getTemperature(companyId);
        Long reviewCount = reviewService.getCountCompanyReview(companyId);

        return CompanyPreviewDto.fromEntity(company, temperatureAvg, reviewCount);
    }
}
