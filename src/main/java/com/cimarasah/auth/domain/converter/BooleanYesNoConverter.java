package com.cimarasah.auth.domain.converter;

import javax.persistence.AttributeConverter;

public class BooleanYesNoConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return (value != null && value) ? "S" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "S".equals(value);
    }
}
