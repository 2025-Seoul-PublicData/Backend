package com.example.seoulpublicdata2025backend.domain.support.controller;

import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDto;
import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementDetailDto;
import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementPreviewDto;
import com.example.seoulpublicdata2025backend.domain.support.service.ConsumerSupportProductService;
import com.example.seoulpublicdata2025backend.domain.support.service.SupportAnnouncementService;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.support.GetConsumerSupportProductsDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.support.SupportDetailDocs;
import com.example.seoulpublicdata2025backend.global.swagger.annotations.support.SupportPreviewDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportAnnouncementController {

    private final SupportAnnouncementService supportAnnouncementService;
    private final ConsumerSupportProductService consumerSupportProductService;

//    지원 공고 프리뷰 리스트를 조회합니다.
//    size 파라미터가 없으면 전체, 있으면 created_at 순으로 size개 가져옵니다.
    @GetMapping("/public/preview")
    @SupportPreviewDocs
    public ResponseEntity<List<SupportAnnouncementPreviewDto>> getSupportAnnouncementPreview(@RequestParam(required = false) Integer size) {
        List<SupportAnnouncementPreviewDto> previews = supportAnnouncementService.getSupportAnnouncementPreview(size);
        return ResponseEntity.ok(previews);
    }

    @GetMapping("/public/detail/{id}")
    @SupportDetailDocs
    public ResponseEntity<SupportAnnouncementDetailDto> getSupportAnnouncementDetail(@PathVariable Integer id) {
        SupportAnnouncementDetailDto detail = supportAnnouncementService.getSupportAnnouncementDetail(id);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/public/consumer/preview")
    @GetConsumerSupportProductsDocs
    public ResponseEntity<List<ConsumerSupportProductResponseDto>> getMemberSupportProducts() {
        List<ConsumerSupportProductResponseDto> response = consumerSupportProductService.getMemberSupportProducts();
        return ResponseEntity.ok(response);
    }
}
