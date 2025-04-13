package com.example.seoulpublicdata2025backend.domain.geocoding.service;

import com.example.seoulpublicdata2025backend.domain.geocoding.dto.MemberLocationDto;

public interface GeocodingService {
    String getNmapSchemeUrl(MemberLocationDto memberLocationDto, String companyName);
}
