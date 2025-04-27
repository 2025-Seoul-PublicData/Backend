package com.example.seoulpublicdata2025backend.domain.company.controller;

import com.example.seoulpublicdata2025backend.domain.company.dto.MemberLocationDto;
import com.example.seoulpublicdata2025backend.domain.company.service.GeocodingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geocoding")
public class GeocodingController {

    private final GeocodingService geocodingService;

    @PostMapping("/nmap")
    public String nmapUrlScheme(@Valid @RequestBody MemberLocationDto dto, @RequestParam Long companyId) {
        return geocodingService.getNmapSchemeUrl(dto, companyId);
    }

}
