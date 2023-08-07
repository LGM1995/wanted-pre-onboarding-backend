package com.task.application;

import com.task.infrastructure.MemberRepository;
import com.task.model.auth.dto.LoginDto;
import com.task.model.auth.dto.TokenDto;
import com.task.model.member.Member;
import com.task.model.member.dto.MemberRequestDto;
import com.task.model.member.dto.MemberResponseDto;
import com.task.model.member.mapper.MemberMapper;
import com.task.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberRepository memberRepository;

    private final CustomMemberDetailsService customMemberDetailsService;

    public TokenDto login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            return TokenDto.builder()
                    .accessToken(jwtTokenProvider.createAccessToken(authentication))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }


    public MemberResponseDto singUp(MemberRequestDto memberRequestDto) {
        if (memberRepository.findByEmail(memberRequestDto.getEmail()) != null) {
            throw new RuntimeException();
        }
        Member member = MemberMapper.toMember(memberRequestDto);

        return MemberMapper.toResponse(memberRepository.save(member));
    }

}
