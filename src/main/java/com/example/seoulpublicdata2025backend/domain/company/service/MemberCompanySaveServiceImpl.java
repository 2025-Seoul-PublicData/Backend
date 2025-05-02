package com.example.seoulpublicdata2025backend.domain.company.service;

import com.example.seoulpublicdata2025backend.domain.company.dao.CompanyRepository;
import com.example.seoulpublicdata2025backend.domain.company.dao.MemberCompanySaveRepository;
import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.Company;
import com.example.seoulpublicdata2025backend.domain.company.entity.MemberCompanySave;
import com.example.seoulpublicdata2025backend.domain.company.entity.MemberCompanySaveId;
import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.global.exception.customException.MemberCompanySaveException;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundCompanyException;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCompanySaveServiceImpl implements MemberCompanySaveService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final MemberCompanySaveRepository memberCompanySaveRepository;

    @Transactional
    @Override
    public void saveCompany(Long companyId) {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        if (memberCompanySaveRepository.existsByKakaoIdAndCompanyId(currentKakaoId, companyId)) {
            throw new MemberCompanySaveException(ErrorCode.DUPLICATE_COMPANY_SAVE);
        }

        Member member = memberRepository.findByKakaoId(currentKakaoId)
                .orElseThrow(() -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundCompanyException(ErrorCode.COMPANY_NOT_FOUND));

        MemberCompanySave like = MemberCompanySave.builder()
                .kakaoId(currentKakaoId)
                .companyId(companyId)
                .member(member)
                .company(company)
                .build();

        memberCompanySaveRepository.save(like);
    }

    @Transactional
    @Override
    public void unSaveCompany(Long companyId) {
        Long currentKakaoId = SecurityUtil.getCurrentMemberKakaoId();

        MemberCompanySaveId id = new MemberCompanySaveId(currentKakaoId, companyId);

        MemberCompanySave save = memberCompanySaveRepository.findById(id)
                .orElseThrow(() -> new MemberCompanySaveException(ErrorCode.COMPANY_SAVE_RECORD_NOT_FOUND));

        memberCompanySaveRepository.delete(save);
    }

    @Override
    public List<CompanyPreviewDto> getSavedCompanies() {
        Long currnetKakaoId = SecurityUtil.getCurrentMemberKakaoId();
        List<MemberCompanySave> saves = memberCompanySaveRepository.findByKakaoId(currnetKakaoId);

        return saves.stream()
                .map(save -> CompanyPreviewDto.fromEntityBasic(save.getCompany()))
                .toList();
    }

    @Override
    public Long countSavedByMember() {
        Long currnetKakaoId = SecurityUtil.getCurrentMemberKakaoId();
        return memberCompanySaveRepository.countByKakaoId(currnetKakaoId);
    }
}
