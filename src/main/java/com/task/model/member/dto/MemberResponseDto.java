package com.task.model.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;

    private String email;

    @Builder
    public MemberResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
