package com.example.seoulpublicdata2025backend.domain.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyLocationTypeDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.company.entity.Location;
import com.example.seoulpublicdata2025backend.domain.member.dao.MemberConsumptionRepository;
import com.example.seoulpublicdata2025backend.domain.member.dao.MemberRepository;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionResponseDto;
import com.example.seoulpublicdata2025backend.domain.member.entity.Member;
import com.example.seoulpublicdata2025backend.domain.member.entity.MemberConsumption;
import com.example.seoulpublicdata2025backend.global.exception.customException.NotFoundMemberException;
import com.example.seoulpublicdata2025backend.global.util.SecurityUtil;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberConsumptionServiceImplTest {

    @InjectMocks
    private MemberConsumptionServiceImpl service;

    @Mock
    private MemberConsumptionRepository memberConsumptionRepository;

    @Mock
    private MemberRepository memberRepository;

    private final Long kakaoId = 12345L;

    private MockedStatic<SecurityUtil> mockedStatic;

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
    @DisplayName("memberConsumption save 성공")
    void saveConsumption_NewEntry_Success() {
        // Given
        CompanyLocationTypeDto companyDto = new CompanyLocationTypeDto(
                -1L,"서울특별시 용산구", CompanyType.ETC, new Location(1,1)
        );
        Member member = Member.builder().kakaoId(kakaoId).build();
        Long totalPrice = 1000L;

        when(memberRepository.findByKakaoId(kakaoId)).thenReturn(Optional.of(member));
        when(memberConsumptionRepository.findByMemberAndCompanyType(member, CompanyType.ETC)).thenReturn(Optional.empty());

        // When
        service.saveConsumption(companyDto, totalPrice);

        // Then
        verify(memberConsumptionRepository).save(any(MemberConsumption.class));
    }

    @Test
    @DisplayName("이전에 구매한 카테고리인 경우 해당 카테고리의 총 결제 금액이 증가함")
    void saveConsumption_ExistingEntry_AddsTotalPrice() {
        // Given
        CompanyLocationTypeDto companyDto = new CompanyLocationTypeDto(
                -1L,"서울특별시 용산구", CompanyType.ETC, new Location(1,1)
        );
        Member member = Member.builder().kakaoId(kakaoId).build();
        Long totalPrice = 1000L;
        MemberConsumption existingConsumption = MemberConsumption.builder()
                .member(member)
                .companyType(CompanyType.ETC)
                .totalPrice(500L)
                .build();

        when(memberRepository.findByKakaoId(kakaoId)).thenReturn(Optional.of(member));
        when(memberConsumptionRepository.findByMemberAndCompanyType(member, CompanyType.ETC)).thenReturn(Optional.of(existingConsumption));

        // When
        service.saveConsumption(companyDto, totalPrice);

        // Then
        assertEquals(1500L, existingConsumption.getTotalPrice());
        verify(memberConsumptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("해당 멤버가 소비한 모든 결제 목록을 가져옴")
    void findConsumptionByMember_ReturnsList() {
        // Given
        MemberConsumption consumption = MemberConsumption.builder()
                .companyType(CompanyType.ETC)
                .totalPrice(1000L)
                .build();

        when(memberConsumptionRepository.findMemberConsumptionByKakaoId(kakaoId))
                .thenReturn(List.of(consumption));

        // When
        List<MemberConsumptionResponseDto> result = service.findConsumptionByMember();

        // Then
        assertEquals(1, result.size());
        assertEquals(1000L, result.get(0).getTotalPrice());
        assertEquals(CompanyType.ETC, result.get(0).getCompanyType());
    }

    @Test
    @DisplayName("해당 멤버의 결제 목록이 없으면 빈 리스트를 반환한다")
    void findConsumptionByMember_ReturnsEmptyList() throws Exception {
        // Given
        when(memberConsumptionRepository.findMemberConsumptionByKakaoId(kakaoId))
                .thenReturn(List.of());

        // When
        List<MemberConsumptionResponseDto> result = service.findConsumptionByMember();

        // Then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("해당 멤버가 특정 카테고리에 소비한 모든 결제 금액 반환")
    void findConsumptionByMemberAndCompanyType_ReturnsFilteredList() {
        // Given
        CompanyType type = CompanyType.ETC;
        MemberConsumption consumption = MemberConsumption.builder()
                .companyType(type)
                .totalPrice(2000L)
                .build();

        when(memberConsumptionRepository.findConsumptionByKakaoIdAndCompanyType(kakaoId, type))
                .thenReturn(Optional.ofNullable(consumption));

        // When
        MemberConsumptionResponseDto result = service.findConsumptionByMemberAndCompanyType(type);

        // Then
        assertEquals(2000L, result.getTotalPrice());
        assertEquals(type, result.getCompanyType());
    }

    @Test
    @DisplayName("카테고리 소비 내역이 없으면 totalPrice = 0 반환")
    void findConsumptionByCompanyType_notFound_returnsZero() {
        when(memberConsumptionRepository.findConsumptionByKakaoIdAndCompanyType(
                kakaoId,
                CompanyType.JOB_PROVISION)
        ).thenReturn(Optional.empty());

        MemberConsumptionResponseDto response =
                service.findConsumptionByMemberAndCompanyType(CompanyType.JOB_PROVISION);

        assertEquals(CompanyType.JOB_PROVISION, response.getCompanyType());
        assertEquals(0L, response.getTotalPrice());
    }

    @Test
    @DisplayName("존재하지 않는 유저에 대한 memberConsumption 저장")
    void saveConsumption_MemberNotFound_ThrowsException() {
        // Given
        when(memberRepository.findByKakaoId(kakaoId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundMemberException.class, () ->
                service.saveConsumption(new CompanyLocationTypeDto(
                        -1L,"서울특별시 용산구", CompanyType.ETC, new Location(1,1)
                ), 1000L));
    }
}