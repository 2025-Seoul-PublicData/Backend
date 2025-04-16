package com.example.seoulpublicdata2025backend.domain.geocoding.controller;

import com.example.seoulpublicdata2025backend.domain.geocoding.dto.MemberLocationDto;
import com.example.seoulpublicdata2025backend.domain.geocoding.service.GeocodingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geocoding")
public class GeocodingController {

    private final GeocodingService geocodingService;

    @PostMapping("/nmap")
    public String nmapUrlScheme(@Valid @RequestBody MemberLocationDto dto, @RequestParam String companyName) {
        return geocodingService.getNmapSchemeUrl(dto, companyName);
    }

}
