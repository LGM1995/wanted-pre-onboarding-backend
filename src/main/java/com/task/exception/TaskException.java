package com.task.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TaskException extends RuntimeException{

    private final ErrorCodeMessage errorCodeMessage;

    public final Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

    public TaskException(ErrorCodeMessage errorCodeMessage) {
        super(errorCodeMessage.getMessage());
        this.errorCodeMessage = errorCodeMessage;
    }
}
