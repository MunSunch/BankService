package com.munsun.calculator.config.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MaskingSerializer extends JsonSerializer<String> {
    @Override
    public void serialize (String value, JsonGenerator gen, SerializerProvider serializers)  throws IOException {
        String maskedValue = maskValue(value);
        gen.writeString(maskedValue);
    }

    private String maskValue (String value) {
        return "***";
    }
}