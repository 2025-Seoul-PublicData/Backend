package com.example.seoulpublicdata2025backend.domain.support.service;

import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementDetailDto;
import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementPreviewDto;

import java.util.List;

public interface SupportAnnouncementService {

    List<SupportAnnouncementPreviewDto> getSupportAnnouncementPreview(Integer size);

    SupportAnnouncementDetailDto getSupportAnnouncementDetail(Integer supportAnnouncementId);
}
