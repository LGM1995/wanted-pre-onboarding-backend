package com.task.util.validator;

import com.task.util.annotation.CommonCheck;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CommonCheckValidator implements ConstraintValidator<CommonCheck, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value);
    }
}
