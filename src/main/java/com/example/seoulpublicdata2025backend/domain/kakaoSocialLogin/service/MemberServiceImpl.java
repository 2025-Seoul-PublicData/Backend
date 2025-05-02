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
        // member 를 찾고 이미 가입된 member 라면 init을 하지 않는다.
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> {
                    Member newMember = Member.init(kakaoId);
                    return memberRepository.save(newMember);
                });

        return new KakaoIdStatusDto(member.getKakaoId(), member.getStatus());
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
