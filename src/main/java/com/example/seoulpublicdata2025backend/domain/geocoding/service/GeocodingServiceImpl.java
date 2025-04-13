package com.example.seoulpublicdata2025backend.domain.geocoding.service;

import com.example.seoulpublicdata2025backend.domain.geocoding.dao.GeocodingRepository;
import com.example.seoulpublicdata2025backend.domain.geocoding.dto.MemberLocationDto;
import com.example.seoulpublicdata2025backend.domain.geocoding.entity.Company;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeocodingServiceImpl implements GeocodingService{

    private final GeocodingRepository geocodingRepository;

    @SneakyThrows
    public String URLEn(String location) {
        return URLEncoder.encode(location, "UTF-8");
    }

    @Override
    public String getNmapSchemeUrl(MemberLocationDto memberLocationDto, String companyName) {

        Optional<Company> companyOptional = geocodingRepository.findByCompanyName(companyName);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();

            double dlat = company.getLatitude();
            double dlng = company.getLongitude();
            String companyLocation = company.getCompanyLocation();
            String dname = URLEn(companyLocation);

            double slat = memberLocationDto.getLatitude();
            double slng = memberLocationDto.getLongitude();
            String memberLocation = memberLocationDto.getLocation();
            String sname = URLEn(memberLocation);

            String nmapUrl = "nmap://route/walk?";
            nmapUrl = nmapUrl + "slat=" + slat + "&slng=" + slng + "&sname=" + sname + "&dlat=" + dlat + "&dlng=" + dlng + "&dname=" + dname + "&appname=com.example.aiml_mobile_2024";

            return nmapUrl;
        } else {
            return "error: Company not found";
        }

    }
}
