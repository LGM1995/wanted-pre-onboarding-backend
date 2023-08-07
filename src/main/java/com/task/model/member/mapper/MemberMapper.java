package com.task.model.member.mapper;

import com.task.model.member.Member;
import com.task.model.member.dto.MemberRequestDto;
import com.task.model.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MemberMapper {

    private static final PasswordEncoder passwordEncoder = null;

    public static Member toMember(MemberRequestDto memberRequestDto) {
        return Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .build();
    }

    public static MemberResponseDto toResponse(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }
}
