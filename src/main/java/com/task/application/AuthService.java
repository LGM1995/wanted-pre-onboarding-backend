package com.task.application;

import com.task.exception.ErrorCodeMessage;
import com.task.exception.TaskException;
import com.task.infrastructure.MemberRepository;
import com.task.model.auth.dto.Login;
import com.task.model.auth.Token;
import com.task.model.member.Member;
import com.task.model.member.dto.MemberRequest;
import com.task.model.member.dto.MemberResponse;
import com.task.model.member.mapper.MemberMapper;
import com.task.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final MemberRepository memberRepository;

    private final CustomMemberDetailsService customMemberDetailsService;

    private final PasswordEncoder passwordEncoder;

    public Token login(Login login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            return Token.builder()
                    .accessToken(jwtTokenProvider.createAccessToken(authentication))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TaskException(ErrorCodeMessage.INVALID_LOGIN);
        }
    }


    public MemberResponse singUp(MemberRequest memberRequest) {
        if (memberRepository.findByEmail(memberRequest.getEmail()).isPresent()) {
            throw new TaskException(ErrorCodeMessage.DUPLICATION_EMAIL);
        }
        Member member = Member.builder()
                .email(memberRequest.getEmail())
                .password(passwordEncoder.encode(memberRequest.getPassword()))
                .build();

        return MemberMapper.toResponse(memberRepository.save(member));
    }

}
