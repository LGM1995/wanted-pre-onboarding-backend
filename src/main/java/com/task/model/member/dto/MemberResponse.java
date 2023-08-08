package com.task.model.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {

    private Long id;

    private String email;

    @Builder
    public MemberResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
