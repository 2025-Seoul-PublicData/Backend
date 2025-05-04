package com.example.seoulpublicdata2025backend.domain.support.service;

import com.example.seoulpublicdata2025backend.domain.story.dto.StoryPreviewDto;
import com.example.seoulpublicdata2025backend.domain.support.dao.SupportAnnouncementRepository;
import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementDetailDto;
import com.example.seoulpublicdata2025backend.domain.support.dto.SupportAnnouncementPreviewDto;
import com.example.seoulpublicdata2025backend.domain.support.entity.SupportAnnouncement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportAnnouncementServiceImpl implements SupportAnnouncementService{

    private final SupportAnnouncementRepository supportAnnouncementRepository;

    @Override
    public List<SupportAnnouncementPreviewDto> getSupportAnnouncementPreview(Integer size) {
        List<SupportAnnouncement> announcements;

        if (size == null) {
            announcements = supportAnnouncementRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        } else {
            announcements = supportAnnouncementRepository
                    .findAll(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt")))
                    .getContent();
        }

        return announcements.stream()
                .map(sa -> new SupportAnnouncementPreviewDto(
                        sa.getId(),
                        sa.getTitle(),
                        sa.getOrganization(),
                        sa.getStartDate(),
                        sa.getEndDate(),
                        sa.getOrganization()
                ))
                .toList();
    }

    @Override
    public SupportAnnouncementDetailDto getSupportAnnouncementDetail(Integer supportAnnouncementId) {
        SupportAnnouncement sa = supportAnnouncementRepository.findById(supportAnnouncementId)
                .orElseThrow(() -> new IllegalArgumentException("해당 지원 공고가 존재하지 않습니다."));

        return new SupportAnnouncementDetailDto(
                sa.getId(),
                sa.getTitle(),
                sa.getOrganization(),
                sa.getStartDate(),
                sa.getEndDate(),
                sa.getAnnouncementType(),
                sa.getSummary(),
                sa.getLink(),
                sa.getCreatedAt()
        );
    }

}
