package com.example.seoulpublicdata2025backend.domain.company.controller;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyMapDto;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.service.CompanyService;
import com.example.seoulpublicdata2025backend.domain.company.service.MemberCompanySaveService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.company.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final MemberCompanySaveService memberCompanySaveService;

    @GetMapping("/public/preview")
    @CompanyPreviewDocs
    public CompanyPreviewDto companyPreview(@RequestParam Long companyId) {
        return companyService.companyPreview(companyId);
    }

    @GetMapping("/public/all-companies")
    @GetAllCompaniesDocs
    public ResponseEntity<List<CompanyMapDto>> getAllCompaniesForMap() {
        return ResponseEntity.ok(companyService.getAllCompany());
    }

    @PatchMapping("/save/{companyId}")
    @SaveCompanyDocs
    public ResponseEntity<Void> saves(@PathVariable Long companyId) {
        memberCompanySaveService.saveCompany(companyId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/unsave/{companyId}")
    @UnsaveCompanyDocs
    public ResponseEntity<Void> unsaves(@PathVariable Long companyId) {
        memberCompanySaveService.unSaveCompany(companyId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/member-saves")
    @GetSavedCompaniesDocs
    public List<CompanyPreviewDto> getSavedCompanies() {
        return memberCompanySaveService.getSavedCompanies();
    }

    @GetMapping("/count-member-saves")
    @CountSavedCompaniesDocs
    public Long countSavedCompanies() {
        return memberCompanySaveService.countSavedByMember();
    }
}
