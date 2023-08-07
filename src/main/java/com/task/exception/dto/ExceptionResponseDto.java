package com.task.exception.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExceptionResponseDto {

    private final Integer code;

    private final String message;

    private final Map<String, String> valid;

    @Builder
    public ExceptionResponseDto(Integer code, String message, Map<String, String> valid) {
        this.code = code;
        this.message = message;
        this.valid = valid != null ? valid : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.valid.put(fieldName, errorMessage);
    }
}
