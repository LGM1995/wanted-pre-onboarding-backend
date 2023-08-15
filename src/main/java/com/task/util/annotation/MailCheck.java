package com.task.util.annotation;

import com.task.util.validator.MailCheckValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MailCheckValidator.class)
public @interface MailCheck {

    String message() default "@를 포함하여야 합니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
