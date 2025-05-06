package com.example.seoulpublicdata2025backend.domain.support.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.seoulpublicdata2025backend.domain.support.dao.ConsumerSupportProductRepository;
import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDto;
import com.example.seoulpublicdata2025backend.domain.support.entity.ConsumerSupportProduct;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsumerSupportProductServiceImplTest {

    @Mock
    private ConsumerSupportProductRepository consumerSupportProductRepository;

    @InjectMocks
    ConsumerSupportProductServiceImpl consumerSupportProductService;

    @Test
    void getMemberSupportProducts_정상조회() {
        // given
        List<ConsumerSupportProduct> products = List.of(
                ConsumerSupportProduct.builder()
                        .bankName("카카오뱅크")
                        .productName("청년 지원 적금")
                        .productDescription("청년을 위한 고금리 적금 상품")
                        .productType("적금")
                        .benefit("금리 우대")
                        .build(),
                ConsumerSupportProduct.builder()
                        .bankName("신한은행")
                        .productName("사회적 기업 대출")
                        .productDescription("사회적 기업을 위한 저금리 대출 상품")
                        .productType("대출")
                        .benefit("이자 지원")
                        .build()
        );

        when(consumerSupportProductRepository.findAll()).thenReturn(products);

        // when
        List<ConsumerSupportProductResponseDto> result = consumerSupportProductService.getMemberSupportProducts();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getBankName()).isEqualTo("카카오뱅크");
        assertThat(result.get(1).getProductName()).isEqualTo("사회적 기업 대출");
    }
}