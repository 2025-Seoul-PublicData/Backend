package com.example.seoulpublicdata2025backend.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.dto.UpdateMemberRequestDto;
import com.example.seoulpublicdata2025backend.domain.member.dto.UpdateMemberResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.exception.errorCode.ErrorCode;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberServiceImpl memberService;

    private MockedStatic<SecurityUtil> mockedStatic;

    private final Long kakaoId = -1L;
    @BeforeEach
    void setUp() {
        mockedStatic = mockStatic(SecurityUtil.class);
        mockedStatic.when(SecurityUtil::getCurrentMemberKakaoId).thenReturn(kakaoId);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    @DisplayName("멤버 프로필 업데이트 성공")
    void updateMember() {
        Member member = Member.builder()
                .kakaoId(kakaoId)
                .name("바뀌기 전")
                .location("바뀌기 전")
                .profileColor("바뀌기 전")
                .build();
        UpdateMemberRequestDto dto = new UpdateMemberRequestDto("바뀌기 후","바뀌기 후","바뀌기 후");
        Mockito.when(memberRepository.findByKakaoId(kakaoId)).thenReturn(Optional.of(member));

        UpdateMemberResponseDto result = memberService.updateMember(dto);

        assertThat(result.getName()).isEqualTo("바뀌기 후");
        assertThat(result.getLocation()).isEqualTo("바뀌기 후");
        assertThat(result.getProfileColor()).isEqualTo("바뀌기 후");
    }

    @Test
    @DisplayName("멤버 프로필 업데이트 실패 - 존재하지 않는 사용자")
    void updateMember_fail_notFoundMember() {
        // given
        UpdateMemberRequestDto dto = new UpdateMemberRequestDto("이름", "위치", "색상");
        Mockito.when(memberRepository.findByKakaoId(kakaoId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> memberService.updateMember(dto))
                .isInstanceOf(NotFoundMemberException.class)
                .hasMessageContaining(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }
}