package com.task.util.annotation;

import com.task.util.validator.PasswordCheckValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordCheckValidator.class})
public @interface PasswordCheck {

    String message() default "비밀번호는 8자리 이상입니다.";

    Class[] groups() default {};

    Class[] payload() default {};

}
