package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dto.MemberLocationDto;

public interface GeocodingService {
    String getNmapSchemeUrl(MemberLocationDto memberLocationDto, Long companyId);
}
