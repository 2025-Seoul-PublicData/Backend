package com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.service;

import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoAuthResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.KakaoIdStatusDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupRequestDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.dto.SignupResponseDto;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.entity.Member;
import com.example.seoulpublicdata2025backend.domain.kakaoSocialLogin.type.MemberStatus;
import com.example.seoulpublicdata2025backend.global.exception.customException.DuplicationMemberException;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public KakaoIdStatusDto initMember(Long kakaoId) {
        Member member = Member.init(kakaoId);
        try {
            memberRepository.save(member);
            return new KakaoIdStatusDto(member.getKakaoId(), member.getStatus());
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicationMemberException(ErrorCode.DUPLICATE_MEMBER);
        }
    }

    @Override
    @Transactional
    public SignupResponseDto updateMember(SignupRequestDto dto) {
        Long kakaoId = SecurityUtil.getCurrentMemberKakaoId();
        Member findMember = memberRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new NotFoundMemberException(ErrorCode.MEMBER_NOT_FOUND));
        findMember.update(dto);
        return SignupResponseDto.from(kakaoId, findMember.getStatus());
    }

    @Override
    public Optional<Member> findByKakaoId(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }

    @Override
    public KakaoAuthResponseDto getMemberStatus(Long kakaoId) {
        boolean result = memberRepository.existsByKakaoId(kakaoId);
        if (result) {
            return KakaoAuthResponseDto.of(MemberStatus.MEMBER);
        }
        return KakaoAuthResponseDto.of(MemberStatus.NOT_MEMBER);
    }

}
