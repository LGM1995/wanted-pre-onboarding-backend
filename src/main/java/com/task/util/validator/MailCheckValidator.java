package com.task.util.validator;

import com.task.util.annotation.MailCheck;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MailCheckValidator implements ConstraintValidator<MailCheck, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value) && value.contains("@");
    }
}
