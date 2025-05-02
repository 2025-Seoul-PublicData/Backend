package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dto.MemberLocationDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundCompanyException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class GeocodingServiceImpl implements GeocodingService{

    private final CompanyRepository companyRepository;

    @SneakyThrows
    public String URLEn(String location) {
        return URLEncoder.encode(location, "UTF-8");
    }

    @Override
    public String getNmapSchemeUrl(MemberLocationDto memberLocationDto, Long companyId) {

        Company company = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND));

        double dlat = company.getLocation().getLatitude();
        double dlng = company.getLocation().getLongitude();
        String companyLocation = company.getCompanyLocation();
        String dname = URLEn(companyLocation);

        double slat = memberLocationDto.getLatitude();
        double slng = memberLocationDto.getLongitude();
        String memberLocation = memberLocationDto.getLocation();
        String sname = URLEn(memberLocation);

        String nmapUrl = "nmap://route/walk?";
        nmapUrl = nmapUrl + "slat=" + slat + "&slng=" + slng + "&sname=" + sname + "&dlat=" + dlat + "&dlng=" + dlng + "&dname=" + dname + "&appname=com.morak.app";

        return nmapUrl;
    }
}
