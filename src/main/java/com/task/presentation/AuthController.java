package com.task.presentation;

import com.task.application.AuthService;
import com.task.model.auth.Token;
import com.task.model.auth.dto.Login;
import com.task.model.member.dto.MemberRequest;
import com.task.model.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Login login) {
        return ResponseEntity.ok(authService.login(login));
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signUp(@RequestBody MemberRequest memberRequest) {
        return ResponseEntity.ok(authService.singUp(memberRequest));
    }
}
