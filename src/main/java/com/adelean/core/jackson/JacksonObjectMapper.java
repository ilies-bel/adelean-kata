package com.adelean.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

public class JacksonObjectMapper {
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .enable(com.fasterxml.jackson.databind.MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS)
            .enable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .addModule(new ParameterNamesModule()) // For supporting Java records serialization
            .propertyNamingStrategy(SNAKE_CASE)
            .build();

    public static ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }
}
