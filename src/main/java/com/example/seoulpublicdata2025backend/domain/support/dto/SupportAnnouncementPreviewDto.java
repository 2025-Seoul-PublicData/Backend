package com.example.seoulpublicdata2025backend.domain.support.dto;

import com.example.seoulpublicdata2025backend.domain.support.entity.SupportAnnouncement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SupportAnnouncementPreviewDto {
    private Integer id;
    private String title;
    private String organization;
    private String startDate;
    private String endDate;
    private String announcementType;
}
