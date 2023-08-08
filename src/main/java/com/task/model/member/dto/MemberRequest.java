package com.task.model.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberRequest {

    @Email(message = "이메일 형식을 지켜주세요.")
    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 입니다.")
    private String password;

    public MemberRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
