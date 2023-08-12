package com.task.model.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class Login {

    @Email(message = "@는 필수 값 입니다.")
    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Length(min = 8, message = "비밀번호는 8자 이상 입니다.")
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
