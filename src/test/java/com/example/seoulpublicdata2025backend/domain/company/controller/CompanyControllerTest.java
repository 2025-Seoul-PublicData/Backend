package com.example.seoulpublicdata2025backend.domain.company.controller;

import com.example.seoulpublicdata2025backend.domain.company.dto.CompanyPreviewDto;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyCategory;
import com.example.seoulpublicdata2025backend.domain.company.entity.CompanyType;
import com.example.seoulpublicdata2025backend.domain.company.service.CompanyService;
import com.example.seoulpublicdata2025backend.global.auth.jwt.JwtParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
@Import(CompanyControllerTest.MockConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtParser jwtParser;

    @TestConfiguration
    static class MockConfig {

        private static final CompanyService companyService = Mockito.mock(CompanyService.class);
        private static final JwtParser jwtParser = Mockito.mock(JwtParser.class);  // 추가

        @Bean
        public CompanyService companyService() {
            return companyService;
        }

        @Bean
        public JwtParser jwtParser() {  // 추가
            return jwtParser;
        }
    }

    @Test
    @DisplayName("회사 프리뷰 조회 성공 테스트")
    void companyPreview() throws Exception {
        // given
        CompanyPreviewDto mockDto = CompanyPreviewDto.fromEntity(
                // Company 객체는 안 만들고 temperature, reviewCount만 테스트니까 가짜 데이터로 채움
                new com.example.seoulpublicdata2025backend.domain.company.entity.Company(
                        1L, "Test Company", "Seoul",
                        "Restaurant", "010-1234-5678",
                        CompanyType.PRE, CompanyCategory.RESTAURANT, null
                ),
                36.5,
                10L
        );

        when(MockConfig.companyService.companyPreview(anyLong()))
                .thenReturn(mockDto);

        // when & then
        mockMvc.perform(get("/company/preview")
                        .param("companyId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(1))
                .andExpect(jsonPath("$.companyName").value("Test Company"))
                .andExpect(jsonPath("$.companyCategory").value("RESTAURANT"))
                .andExpect(jsonPath("$.temperature").value(36.5))
                .andExpect(jsonPath("$.reviewCount").value(10))
                .andExpect(jsonPath("$.companyLocation").value("Seoul"))
                .andExpect(jsonPath("$.business").value("Restaurant"))
                .andExpect(jsonPath("$.companyType").value("PRE"));
    }
}
