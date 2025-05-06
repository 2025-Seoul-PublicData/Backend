package com.example.seoulpublicdata2025backend.domain.support.service;

import com.example.seoulpublicdata2025backend.domain.support.dto.ConsumerSupportProductResponseDto;

import java.util.List;

public interface ConsumerSupportProductService {
    List<ConsumerSupportProductResponseDto> getMemberSupportProducts();
}
