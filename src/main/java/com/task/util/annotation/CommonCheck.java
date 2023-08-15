package com.task.util.annotation;

import com.task.util.validator.CommonCheckValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CommonCheckValidator.class)
public @interface CommonCheck {

    String message() default "필수 값 입니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
