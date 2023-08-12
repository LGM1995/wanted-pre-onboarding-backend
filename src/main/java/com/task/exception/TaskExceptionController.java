package com.task.exception;

import com.task.exception.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TaskExceptionController {

    @ExceptionHandler(TaskException.class)
    public ExceptionResponse TaskException(TaskException e) {
        return ExceptionResponse.builder()
                .code(e.getErrorCodeMessage().getStatus())
                .message(e.getMessage())
                .valid(e.getValidation())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(400)
                .message("잘못된 요청입니다.")
                .build();
        for (FieldError fieldError : e.getFieldErrors()) {
            exceptionResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return exceptionResponse;
    }

    @ExceptionHandler(BindException.class)
    public ExceptionResponse bindExceptionHandler(BindException e) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(400)
                .message("잘못된 요청입니다.")
                .build();
        for (FieldError fieldError : e.getFieldErrors()) {
            exceptionResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return exceptionResponse;
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse exceptionHandler(Exception e) {
        return ExceptionResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();
    }
}
