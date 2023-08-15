package com.task.model.auth.dto;

import com.task.util.annotation.MailCheck;
import com.task.util.annotation.PasswordCheck;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class Login {

    @MailCheck
    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    private String email;

    @PasswordCheck
    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
