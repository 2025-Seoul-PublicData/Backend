package com.example.seoulpublicdata2025backend.domain.company.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CompanyTypeConverter implements AttributeConverter<CompanyType, String> {

    @Override
    public String convertToDatabaseColumn(CompanyType attribute) {
        return attribute != null ? attribute.getKoreanName() : null;
    }

    @Override
    public CompanyType convertToEntityAttribute(String dbData) {
        return dbData != null ? CompanyType.fromKorean(dbData) : null;
    }
}
