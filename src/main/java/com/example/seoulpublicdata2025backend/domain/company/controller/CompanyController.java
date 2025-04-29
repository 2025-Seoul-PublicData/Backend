package com.example.seoulpublicdata2025backend.domain.company.controller;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.service.CompanyService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.company.CompanyPreviewDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/preview")
    @CompanyPreviewDocs
    public CompanyPreviewDto companyPreview(@RequestParam Long companyId) {
        return companyService.companyPreview(companyId);
    }
}
