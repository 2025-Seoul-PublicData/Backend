package com.example.seoulpublicdata2025backend.domain.support.service;

import com.example.seoulpublicdata2025backend.domain.support.dao.ConsumerSupportProductRepository;
import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDto;
import com.example.seoulpublicdata2025backend.domain.support.entity.ConsumerSupportProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ConsumerSupportProductServiceImpl implements ConsumerSupportProductService {

    private final ConsumerSupportProductRepository consumerSupportProductRepository;

    @Override
    @Transactional
    public List<ConsumerSupportProductResponseDto> getMemberSupportProducts() {
        List<ConsumerSupportProduct> consumerSupportProducts = consumerSupportProductRepository.findAll();
        return consumerSupportProducts.stream()
                .map(support -> ConsumerSupportProductResponseDto.builder()
                        .bankName(support.getBankName())
                        .productName(support.getProductName())
                        .productDescription(support.getProductDescription())
                        .productType(support.getProductType())
                        .benefit(support.getBenefit())
                        .build()
                ).toList();
    }
}
