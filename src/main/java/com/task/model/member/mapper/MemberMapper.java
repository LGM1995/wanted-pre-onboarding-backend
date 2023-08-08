package com.task.model.member.mapper;

import com.task.model.member.Member;
import com.task.model.member.dto.MemberResponse;

public class MemberMapper {

    public static MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }
}
