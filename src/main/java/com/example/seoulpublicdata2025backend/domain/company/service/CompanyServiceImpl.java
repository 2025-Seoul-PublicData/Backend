package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.review.dao.CompanyReviewRepository;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyReviewRepository companyReviewRepository;
    private final ReviewService reviewService;

    @Override
    public CompanyPreviewDto companyPreview(Long companyId) {
        Company company = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Not Exist CompanyId: " + companyId));

        Double temperatureAvg = reviewService.getTemperature(companyId);
        Long reviewCount = reviewService.getCountCompanyReview(companyId);

        return CompanyPreviewDto.fromEntity(company, temperatureAvg, reviewCount);
    }
}
