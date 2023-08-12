package com.task.application;

import com.task.exception.ErrorCodeMessage;
import com.task.exception.TaskException;
import com.task.infrastructure.MemberRepository;
import com.task.model.auth.dto.Login;
import com.task.model.auth.Token;
import com.task.model.member.Member;
import com.task.model.member.dto.MemberRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("Success SignUp")
    void Success_SignUp() {
        String email = "test@test.com";
        String password = "test1234!";

        MemberRequest memberRequest = new MemberRequest(email, password);

        authService.singUp(memberRequest);

        assertEquals(1, memberRepository.count());

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        assertEquals(email, member.getEmail());

    }

    @Test
    @DisplayName("Duplicate registration")
    void Duplicate_registration() {
        Member member = Member.builder()
                .email("test@test.com")
                .password("test1234!")
                .build();
        memberRepository.save(member);

        MemberRequest memberRequest = new MemberRequest("test@test.com", "test4321@");

        assertThrows(TaskException.class, () -> authService.singUp(memberRequest));
    }

    @Test
    @DisplayName("Success Login")
    void Success_Login() {
        String password = "test1234!";
        String encodingPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email("test@test.com")
                .password(encodingPassword)
                .build();
        memberRepository.save(member);

        Login login = new Login("test@test.com", password);

        Token token = authService.login(login);

        assertNotNull(token.getAccessToken());
    }

    @Test
    @DisplayName("Password Failed")
    void Password_Failed() {

        String password = "test1234!";
        String encodingPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email("test@test.com")
                .password(encodingPassword)
                .build();
        memberRepository.save(member);

        Login login = new Login("test@test.com", "test4321!");

        assertThrows(TaskException.class, () -> authService.login(login));

    }
}
