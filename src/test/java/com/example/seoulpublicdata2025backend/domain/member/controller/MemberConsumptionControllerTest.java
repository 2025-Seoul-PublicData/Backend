package com.example.seoulpublicdata2025backend.domain.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.member.dto.MemberConsumptionDto;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberConsumptionService;
import com.example.seoulpublicdata2025backend.domain.member.service.MemberService;
import com.example.seoulpublicdata2025backend.domain.review.service.ReviewService;
import com.example.seoulpublicdata2025backend.global.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class MemberConsumptionControllerTest {

    @Mock
    MemberService memberService;

    @Mock
    ReviewService reviewService;

    @Mock
    MemberConsumptionService memberConsumptionService;

    @InjectMocks
    MemberConsumptionController memberConsumptionController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(memberConsumptionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    @DisplayName("MemberConsumption 조회 성공")
    void getMemberConsumption_shouldReturnList() throws Exception {
        List<MemberConsumptionDto> mockList = List.of(
                MemberConsumptionDto.builder()
                        .companyType(CompanyType.MIXED)
                        .totalPrice(10000L)
                        .build()
        );

        when(memberConsumptionService.findConsumptionByMember()).thenReturn(mockList);
        when(memberService.findMemberName()).thenReturn("test-user");
        when(reviewService.getCountMemberReview()).thenReturn(5L);

        mockMvc.perform(get("/member/consumption"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test-user"))
                .andExpect(jsonPath("$.reviewCount").value(5))
                .andExpect(jsonPath("$.consumptions[0].companyType").value("MIXED"))
                .andExpect(jsonPath("$.consumptions[0].totalPrice").value(10000));
    }

    @Test
    @DisplayName("카테고리에 대한 MemberConsumption 조회 성공")
    void getMemberConsumptionDetail_shouldReturnDto() throws Exception {
        MemberConsumptionDto dto = MemberConsumptionDto.builder()
                .companyType(CompanyType.MIXED)
                .totalPrice(5000L)
                .build();

        when(memberConsumptionService.findConsumptionByMemberAndCompanyType(CompanyType.MIXED)).thenReturn(dto);
        when(memberService.findMemberName()).thenReturn("test-user");
        when(reviewService.getCountMemberReview()).thenReturn(5L);

        mockMvc.perform(get("/member/consumption/detail")
                        .param("companyType", "MIXED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test-user"))
                .andExpect(jsonPath("$.reviewCount").value(5))
                .andExpect(jsonPath("$.consumption.totalPrice").value(5000))
                .andExpect(jsonPath("$.consumption.companyType").value("MIXED"));
    }

}